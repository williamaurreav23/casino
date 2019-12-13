// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { SpielerComponentsPage, SpielerDeleteDialog, SpielerUpdatePage } from './spieler.page-object';

const expect = chai.expect;

describe('Spieler e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let spielerComponentsPage: SpielerComponentsPage;
  let spielerUpdatePage: SpielerUpdatePage;
  let spielerDeleteDialog: SpielerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Spielers', async () => {
    await navBarPage.goToEntity('spieler');
    spielerComponentsPage = new SpielerComponentsPage();
    await browser.wait(ec.visibilityOf(spielerComponentsPage.title), 5000);
    expect(await spielerComponentsPage.getTitle()).to.eq('casinoApp.spieler.home.title');
  });

  it('should load create Spieler page', async () => {
    await spielerComponentsPage.clickOnCreateButton();
    spielerUpdatePage = new SpielerUpdatePage();
    expect(await spielerUpdatePage.getPageTitle()).to.eq('casinoApp.spieler.home.createOrEditLabel');
    await spielerUpdatePage.cancel();
  });

  it('should create and save Spielers', async () => {
    const nbButtonsBeforeCreate = await spielerComponentsPage.countDeleteButtons();

    await spielerComponentsPage.clickOnCreateButton();
    await promise.all([
      spielerUpdatePage.setVornameInput('vorname'),
      spielerUpdatePage.setNachnameInput('nachname'),
      spielerUpdatePage.setKennzahlInput('5')
    ]);
    expect(await spielerUpdatePage.getVornameInput()).to.eq('vorname', 'Expected Vorname value to be equals to vorname');
    expect(await spielerUpdatePage.getNachnameInput()).to.eq('nachname', 'Expected Nachname value to be equals to nachname');
    const selectedIsKind = spielerUpdatePage.getIsKindInput();
    if (await selectedIsKind.isSelected()) {
      await spielerUpdatePage.getIsKindInput().click();
      expect(await spielerUpdatePage.getIsKindInput().isSelected(), 'Expected isKind not to be selected').to.be.false;
    } else {
      await spielerUpdatePage.getIsKindInput().click();
      expect(await spielerUpdatePage.getIsKindInput().isSelected(), 'Expected isKind to be selected').to.be.true;
    }
    expect(await spielerUpdatePage.getKennzahlInput()).to.eq('5', 'Expected kennzahl value to be equals to 5');
    await spielerUpdatePage.save();
    expect(await spielerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await spielerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Spieler', async () => {
    const nbButtonsBeforeDelete = await spielerComponentsPage.countDeleteButtons();
    await spielerComponentsPage.clickOnLastDeleteButton();

    spielerDeleteDialog = new SpielerDeleteDialog();
    expect(await spielerDeleteDialog.getDialogTitle()).to.eq('casinoApp.spieler.delete.question');
    await spielerDeleteDialog.clickOnConfirmButton();

    expect(await spielerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
