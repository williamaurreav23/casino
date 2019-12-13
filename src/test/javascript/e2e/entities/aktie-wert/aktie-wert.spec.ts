// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AktieWertComponentsPage, AktieWertDeleteDialog, AktieWertUpdatePage } from './aktie-wert.page-object';

const expect = chai.expect;

describe('AktieWert e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let aktieWertComponentsPage: AktieWertComponentsPage;
  let aktieWertUpdatePage: AktieWertUpdatePage;
  let aktieWertDeleteDialog: AktieWertDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AktieWerts', async () => {
    await navBarPage.goToEntity('aktie-wert');
    aktieWertComponentsPage = new AktieWertComponentsPage();
    await browser.wait(ec.visibilityOf(aktieWertComponentsPage.title), 5000);
    expect(await aktieWertComponentsPage.getTitle()).to.eq('casinoApp.aktieWert.home.title');
  });

  it('should load create AktieWert page', async () => {
    await aktieWertComponentsPage.clickOnCreateButton();
    aktieWertUpdatePage = new AktieWertUpdatePage();
    expect(await aktieWertUpdatePage.getPageTitle()).to.eq('casinoApp.aktieWert.home.createOrEditLabel');
    await aktieWertUpdatePage.cancel();
  });

  it('should create and save AktieWerts', async () => {
    const nbButtonsBeforeCreate = await aktieWertComponentsPage.countDeleteButtons();

    await aktieWertComponentsPage.clickOnCreateButton();
    await promise.all([aktieWertUpdatePage.setWertInput('5'), aktieWertUpdatePage.aktieSelectLastOption()]);
    expect(await aktieWertUpdatePage.getWertInput()).to.eq('5', 'Expected wert value to be equals to 5');
    await aktieWertUpdatePage.save();
    expect(await aktieWertUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await aktieWertComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AktieWert', async () => {
    const nbButtonsBeforeDelete = await aktieWertComponentsPage.countDeleteButtons();
    await aktieWertComponentsPage.clickOnLastDeleteButton();

    aktieWertDeleteDialog = new AktieWertDeleteDialog();
    expect(await aktieWertDeleteDialog.getDialogTitle()).to.eq('casinoApp.aktieWert.delete.question');
    await aktieWertDeleteDialog.clickOnConfirmButton();

    expect(await aktieWertComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
