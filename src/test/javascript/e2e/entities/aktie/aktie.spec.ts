// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AktieComponentsPage, AktieDeleteDialog, AktieUpdatePage } from './aktie.page-object';

const expect = chai.expect;

describe('Aktie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let aktieComponentsPage: AktieComponentsPage;
  let aktieUpdatePage: AktieUpdatePage;
  let aktieDeleteDialog: AktieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Akties', async () => {
    await navBarPage.goToEntity('aktie');
    aktieComponentsPage = new AktieComponentsPage();
    await browser.wait(ec.visibilityOf(aktieComponentsPage.title), 5000);
    expect(await aktieComponentsPage.getTitle()).to.eq('casinoApp.aktie.home.title');
  });

  it('should load create Aktie page', async () => {
    await aktieComponentsPage.clickOnCreateButton();
    aktieUpdatePage = new AktieUpdatePage();
    expect(await aktieUpdatePage.getPageTitle()).to.eq('casinoApp.aktie.home.createOrEditLabel');
    await aktieUpdatePage.cancel();
  });

  it('should create and save Akties', async () => {
    const nbButtonsBeforeCreate = await aktieComponentsPage.countDeleteButtons();

    await aktieComponentsPage.clickOnCreateButton();
    await promise.all([aktieUpdatePage.setNameInput('name')]);
    expect(await aktieUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await aktieUpdatePage.save();
    expect(await aktieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await aktieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Aktie', async () => {
    const nbButtonsBeforeDelete = await aktieComponentsPage.countDeleteButtons();
    await aktieComponentsPage.clickOnLastDeleteButton();

    aktieDeleteDialog = new AktieDeleteDialog();
    expect(await aktieDeleteDialog.getDialogTitle()).to.eq('casinoApp.aktie.delete.question');
    await aktieDeleteDialog.clickOnConfirmButton();

    expect(await aktieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
