// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AktieWertHistoryComponentsPage, AktieWertHistoryDeleteDialog, AktieWertHistoryUpdatePage } from './aktie-wert-history.page-object';

const expect = chai.expect;

describe('AktieWertHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let aktieWertHistoryComponentsPage: AktieWertHistoryComponentsPage;
  let aktieWertHistoryUpdatePage: AktieWertHistoryUpdatePage;
  let aktieWertHistoryDeleteDialog: AktieWertHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AktieWertHistories', async () => {
    await navBarPage.goToEntity('aktie-wert-history');
    aktieWertHistoryComponentsPage = new AktieWertHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(aktieWertHistoryComponentsPage.title), 5000);
    expect(await aktieWertHistoryComponentsPage.getTitle()).to.eq('casinoApp.aktieWertHistory.home.title');
  });

  it('should load create AktieWertHistory page', async () => {
    await aktieWertHistoryComponentsPage.clickOnCreateButton();
    aktieWertHistoryUpdatePage = new AktieWertHistoryUpdatePage();
    expect(await aktieWertHistoryUpdatePage.getPageTitle()).to.eq('casinoApp.aktieWertHistory.home.createOrEditLabel');
    await aktieWertHistoryUpdatePage.cancel();
  });

  it('should create and save AktieWertHistories', async () => {
    const nbButtonsBeforeCreate = await aktieWertHistoryComponentsPage.countDeleteButtons();

    await aktieWertHistoryComponentsPage.clickOnCreateButton();
    await promise.all([
      aktieWertHistoryUpdatePage.setWertInput('5'),
      aktieWertHistoryUpdatePage.setCreationTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      aktieWertHistoryUpdatePage.aktieSelectLastOption()
    ]);
    expect(await aktieWertHistoryUpdatePage.getWertInput()).to.eq('5', 'Expected wert value to be equals to 5');
    expect(await aktieWertHistoryUpdatePage.getCreationTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationTime value to be equals to 2000-12-31'
    );
    await aktieWertHistoryUpdatePage.save();
    expect(await aktieWertHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await aktieWertHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AktieWertHistory', async () => {
    const nbButtonsBeforeDelete = await aktieWertHistoryComponentsPage.countDeleteButtons();
    await aktieWertHistoryComponentsPage.clickOnLastDeleteButton();

    aktieWertHistoryDeleteDialog = new AktieWertHistoryDeleteDialog();
    expect(await aktieWertHistoryDeleteDialog.getDialogTitle()).to.eq('casinoApp.aktieWertHistory.delete.question');
    await aktieWertHistoryDeleteDialog.clickOnConfirmButton();

    expect(await aktieWertHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
