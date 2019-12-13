import { element, by, ElementFinder } from 'protractor';

export class SpielerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-spieler div table .btn-danger'));
  title = element.all(by.css('jhi-spieler div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class SpielerUpdatePage {
  pageTitle = element(by.id('jhi-spieler-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  vornameInput = element(by.id('field_vorname'));
  nachnameInput = element(by.id('field_nachname'));
  isKindInput = element(by.id('field_isKind'));
  kennzahlInput = element(by.id('field_kennzahl'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVornameInput(vorname) {
    await this.vornameInput.sendKeys(vorname);
  }

  async getVornameInput() {
    return await this.vornameInput.getAttribute('value');
  }

  async setNachnameInput(nachname) {
    await this.nachnameInput.sendKeys(nachname);
  }

  async getNachnameInput() {
    return await this.nachnameInput.getAttribute('value');
  }

  getIsKindInput(timeout?: number) {
    return this.isKindInput;
  }
  async setKennzahlInput(kennzahl) {
    await this.kennzahlInput.sendKeys(kennzahl);
  }

  async getKennzahlInput() {
    return await this.kennzahlInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SpielerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-spieler-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-spieler'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
