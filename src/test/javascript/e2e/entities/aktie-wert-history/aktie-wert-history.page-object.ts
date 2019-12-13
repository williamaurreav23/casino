import { element, by, ElementFinder } from 'protractor';

export class AktieWertHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-aktie-wert-history div table .btn-danger'));
  title = element.all(by.css('jhi-aktie-wert-history div h2#page-heading span')).first();

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

export class AktieWertHistoryUpdatePage {
  pageTitle = element(by.id('jhi-aktie-wert-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  wertInput = element(by.id('field_wert'));
  creationTimeInput = element(by.id('field_creationTime'));
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

  async setCreationTimeInput(creationTime) {
    await this.creationTimeInput.sendKeys(creationTime);
  }

  async getCreationTimeInput() {
    return await this.creationTimeInput.getAttribute('value');
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

export class AktieWertHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-aktieWertHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-aktieWertHistory'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
