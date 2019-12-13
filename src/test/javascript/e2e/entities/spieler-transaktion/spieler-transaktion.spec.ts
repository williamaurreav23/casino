// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  SpielerTransaktionComponentsPage,
  SpielerTransaktionDeleteDialog,
  SpielerTransaktionUpdatePage
} from './spieler-transaktion.page-object';

const expect = chai.expect;

describe('SpielerTransaktion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let spielerTransaktionComponentsPage: SpielerTransaktionComponentsPage;
  let spielerTransaktionUpdatePage: SpielerTransaktionUpdatePage;
  let spielerTransaktionDeleteDialog: SpielerTransaktionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SpielerTransaktions', async () => {
    await navBarPage.goToEntity('spieler-transaktion');
    spielerTransaktionComponentsPage = new SpielerTransaktionComponentsPage();
    await browser.wait(ec.visibilityOf(spielerTransaktionComponentsPage.title), 5000);
    expect(await spielerTransaktionComponentsPage.getTitle()).to.eq('casinoApp.spielerTransaktion.home.title');
  });

  it('should load create SpielerTransaktion page', async () => {
    await spielerTransaktionComponentsPage.clickOnCreateButton();
    spielerTransaktionUpdatePage = new SpielerTransaktionUpdatePage();
    expect(await spielerTransaktionUpdatePage.getPageTitle()).to.eq('casinoApp.spielerTransaktion.home.createOrEditLabel');
    await spielerTransaktionUpdatePage.cancel();
  });

  it('should create and save SpielerTransaktions', async () => {
    const nbButtonsBeforeCreate = await spielerTransaktionComponentsPage.countDeleteButtons();

    await spielerTransaktionComponentsPage.clickOnCreateButton();
    await promise.all([
      spielerTransaktionUpdatePage.setWertInput('5'),
      spielerTransaktionUpdatePage.typSelectLastOption(),
      spielerTransaktionUpdatePage.spielerSelectLastOption()
    ]);
    expect(await spielerTransaktionUpdatePage.getWertInput()).to.eq('5', 'Expected wert value to be equals to 5');
    await spielerTransaktionUpdatePage.save();
    expect(await spielerTransaktionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await spielerTransaktionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SpielerTransaktion', async () => {
    const nbButtonsBeforeDelete = await spielerTransaktionComponentsPage.countDeleteButtons();
    await spielerTransaktionComponentsPage.clickOnLastDeleteButton();

    spielerTransaktionDeleteDialog = new SpielerTransaktionDeleteDialog();
    expect(await spielerTransaktionDeleteDialog.getDialogTitle()).to.eq('casinoApp.spielerTransaktion.delete.question');
    await spielerTransaktionDeleteDialog.clickOnConfirmButton();

    expect(await spielerTransaktionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
