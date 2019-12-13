// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { SpielerAktieComponentsPage, SpielerAktieDeleteDialog, SpielerAktieUpdatePage } from './spieler-aktie.page-object';

const expect = chai.expect;

describe('SpielerAktie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let spielerAktieComponentsPage: SpielerAktieComponentsPage;
  let spielerAktieUpdatePage: SpielerAktieUpdatePage;
  let spielerAktieDeleteDialog: SpielerAktieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SpielerAkties', async () => {
    await navBarPage.goToEntity('spieler-aktie');
    spielerAktieComponentsPage = new SpielerAktieComponentsPage();
    await browser.wait(ec.visibilityOf(spielerAktieComponentsPage.title), 5000);
    expect(await spielerAktieComponentsPage.getTitle()).to.eq('casinoApp.spielerAktie.home.title');
  });

  it('should load create SpielerAktie page', async () => {
    await spielerAktieComponentsPage.clickOnCreateButton();
    spielerAktieUpdatePage = new SpielerAktieUpdatePage();
    expect(await spielerAktieUpdatePage.getPageTitle()).to.eq('casinoApp.spielerAktie.home.createOrEditLabel');
    await spielerAktieUpdatePage.cancel();
  });

  it('should create and save SpielerAkties', async () => {
    const nbButtonsBeforeCreate = await spielerAktieComponentsPage.countDeleteButtons();

    await spielerAktieComponentsPage.clickOnCreateButton();
    await promise.all([
      spielerAktieUpdatePage.setAnzahlInput('5'),
      spielerAktieUpdatePage.spielerSelectLastOption(),
      spielerAktieUpdatePage.aktieSelectLastOption()
    ]);
    expect(await spielerAktieUpdatePage.getAnzahlInput()).to.eq('5', 'Expected anzahl value to be equals to 5');
    await spielerAktieUpdatePage.save();
    expect(await spielerAktieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await spielerAktieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SpielerAktie', async () => {
    const nbButtonsBeforeDelete = await spielerAktieComponentsPage.countDeleteButtons();
    await spielerAktieComponentsPage.clickOnLastDeleteButton();

    spielerAktieDeleteDialog = new SpielerAktieDeleteDialog();
    expect(await spielerAktieDeleteDialog.getDialogTitle()).to.eq('casinoApp.spielerAktie.delete.question');
    await spielerAktieDeleteDialog.clickOnConfirmButton();

    expect(await spielerAktieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
