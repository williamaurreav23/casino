import { element, by, ElementFinder } from 'protractor';

export class SpielerTransaktionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-spieler-transaktion div table .btn-danger'));
  title = element.all(by.css('jhi-spieler-transaktion div h2#page-heading span')).first();

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

export class SpielerTransaktionUpdatePage {
  pageTitle = element(by.id('jhi-spieler-transaktion-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  wertInput = element(by.id('field_wert'));
  typSelect = element(by.id('field_typ'));
  spielerSelect = element(by.id('field_spieler'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setWertInput(wert) {
    await this.wertInput.sendKeys(wert);
  }

  async getWertInput() {
    return await this.wertInput.getAttribute('value');
  }

  async setTypSelect(typ) {
    await this.typSelect.sendKeys(typ);
  }

  async getTypSelect() {
    return await this.typSelect.element(by.css('option:checked')).getText();
  }

  async typSelectLastOption(timeout?: number) {
    await this.typSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class SpielerTransaktionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-spielerTransaktion-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-spielerTransaktion'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
