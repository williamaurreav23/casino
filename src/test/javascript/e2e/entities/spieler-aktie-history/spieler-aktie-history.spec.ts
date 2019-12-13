// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  SpielerAktieHistoryComponentsPage,
  SpielerAktieHistoryDeleteDialog,
  SpielerAktieHistoryUpdatePage
} from './spieler-aktie-history.page-object';

const expect = chai.expect;

describe('SpielerAktieHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let spielerAktieHistoryComponentsPage: SpielerAktieHistoryComponentsPage;
  let spielerAktieHistoryUpdatePage: SpielerAktieHistoryUpdatePage;
  let spielerAktieHistoryDeleteDialog: SpielerAktieHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SpielerAktieHistories', async () => {
    await navBarPage.goToEntity('spieler-aktie-history');
    spielerAktieHistoryComponentsPage = new SpielerAktieHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(spielerAktieHistoryComponentsPage.title), 5000);
    expect(await spielerAktieHistoryComponentsPage.getTitle()).to.eq('casinoApp.spielerAktieHistory.home.title');
  });

  it('should load create SpielerAktieHistory page', async () => {
    await spielerAktieHistoryComponentsPage.clickOnCreateButton();
    spielerAktieHistoryUpdatePage = new SpielerAktieHistoryUpdatePage();
    expect(await spielerAktieHistoryUpdatePage.getPageTitle()).to.eq('casinoApp.spielerAktieHistory.home.createOrEditLabel');
    await spielerAktieHistoryUpdatePage.cancel();
  });

  it('should create and save SpielerAktieHistories', async () => {
    const nbButtonsBeforeCreate = await spielerAktieHistoryComponentsPage.countDeleteButtons();

    await spielerAktieHistoryComponentsPage.clickOnCreateButton();
    await promise.all([
      spielerAktieHistoryUpdatePage.setAnzahlInput('5'),
      spielerAktieHistoryUpdatePage.setCreationTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      spielerAktieHistoryUpdatePage.spielerSelectLastOption(),
      spielerAktieHistoryUpdatePage.aktieSelectLastOption()
    ]);
    expect(await spielerAktieHistoryUpdatePage.getAnzahlInput()).to.eq('5', 'Expected anzahl value to be equals to 5');
    expect(await spielerAktieHistoryUpdatePage.getCreationTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationTime value to be equals to 2000-12-31'
    );
    await spielerAktieHistoryUpdatePage.save();
    expect(await spielerAktieHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await spielerAktieHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SpielerAktieHistory', async () => {
    const nbButtonsBeforeDelete = await spielerAktieHistoryComponentsPage.countDeleteButtons();
    await spielerAktieHistoryComponentsPage.clickOnLastDeleteButton();

    spielerAktieHistoryDeleteDialog = new SpielerAktieHistoryDeleteDialog();
    expect(await spielerAktieHistoryDeleteDialog.getDialogTitle()).to.eq('casinoApp.spielerAktieHistory.delete.question');
    await spielerAktieHistoryDeleteDialog.clickOnConfirmButton();

    expect(await spielerAktieHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
