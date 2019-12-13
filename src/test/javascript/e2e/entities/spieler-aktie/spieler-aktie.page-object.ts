import { element, by, ElementFinder } from 'protractor';

export class SpielerAktieComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-spieler-aktie div table .btn-danger'));
  title = element.all(by.css('jhi-spieler-aktie div h2#page-heading span')).first();

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

export class SpielerAktieUpdatePage {
  pageTitle = element(by.id('jhi-spieler-aktie-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  anzahlInput = element(by.id('field_anzahl'));
  spielerSelect = element(by.id('field_spieler'));
  aktieSelect = element(by.id('field_aktie'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAnzahlInput(anzahl) {
    await this.anzahlInput.sendKeys(anzahl);
  }

  async getAnzahlInput() {
    return await this.anzahlInput.getAttribute('value');
  }

  async spielerSelectLastOption(timeout?: number) {
    await this.spielerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async spielerSelectOption(option) {
    await this.spielerSelect.sendKeys(option);
  }

  getSpielerSelect(): ElementFinder {
    return this.spielerSelect;
  }

  async getSpielerSelectedOption() {
    return await this.spielerSelect.element(by.css('option:checked')).getText();
  }

  async aktieSelectLastOption(timeout?: number) {
    await this.aktieSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async aktieSelectOption(option) {
    await this.aktieSelect.sendKeys(option);
  }

  getAktieSelect(): ElementFinder {
    return this.aktieSelect;
  }

  async getAktieSelectedOption() {
    return await this.aktieSelect.element(by.css('option:checked')).getText();
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

export class SpielerAktieDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-spielerAktie-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-spielerAktie'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
