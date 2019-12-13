import { element, by, ElementFinder } from 'protractor';

export class AktieWertComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-aktie-wert div table .btn-danger'));
  title = element.all(by.css('jhi-aktie-wert div h2#page-heading span')).first();

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

export class AktieWertUpdatePage {
  pageTitle = element(by.id('jhi-aktie-wert-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  wertInput = element(by.id('field_wert'));
  aktieSelect = element(by.id('field_aktie'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setWertInput(wert) {
    await this.wertInput.sendKeys(wert);
  }

  async getWertInput() {
    return await this.wertInput.getAttribute('value');
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

export class AktieWertDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-aktieWert-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-aktieWert'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
