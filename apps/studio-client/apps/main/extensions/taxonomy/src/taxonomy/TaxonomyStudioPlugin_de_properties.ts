import ResourceBundleUtil from "@jangaroo/runtime/l10n/ResourceBundleUtil";
import TaxonomyStudioPlugin_properties from "./TaxonomyStudioPlugin_properties";

/**
 * Overrides of ResourceBundle "TaxonomyStudioPlugin" for Locale "de".
 * @see TaxonomyStudioPlugin_properties#INSTANCE
 */
ResourceBundleUtil.override(TaxonomyStudioPlugin_properties, {
  TaxonomySearch_empty_text: "Geben Sie hier ein Schlagwort ein.",
  TaxonomySearch_empty_linklist_text: "Geben Sie hier ein Schlagwort ein oder ziehen Sie Vorschläge hierher.",
  TaxonomySearch_empty_search_text: "Schlagwortsuche…",
  TaxonomySearch_loading_text: "Laden…",
  TaxonomySearch_no_hit: "Keine Suchergebnisse",
  TaxonomyEditor_deletion_blocked_text: "Die selektierten Schlagwörter oder deren Kind-Schlagwörter sind in {0} Dokumenten verlinkt. Bitte entfernen Sie diese Verknüpfungen bevor Sie die ausgewählten Schlagworte löschen.",
  TaxonomyEditor_deletion_failed_text: "Fehler beim Löschen Schlagwörter: {0}<br><br> Bitte überprüfen Sie die Log-Datei auf dem Server.",
  TaxonomyEditor_deletion_failed_title: "Fehler",
  TaxonomyEditor_deletion_text: "Möchten Sie die augewählten Schlagworte wirklich löschen?",
  TaxonomyEditor_deletion_text_referrer_warning: "Die selektierten Schlagwörter oder deren Kind-Schlagwörter sind noch in {0} weiteren Dokumenten verlinkt.<br/><br/>Möchten Sie die ausgewählten Schlagworte wirklich löschen?",
  TaxonomyEditor_deletion_title: "Schlagworte löschen",
  TaxonomyEditor_title: "Schlagworte",
  TaxonomyExplorerPanel_add_button_label: "Kind-Schlagwort hinzufügen",
  TaxonomyExplorerPanel_reload_button_label: "Schlagworte neu laden",
  TaxonomyExplorerPanel_remove_button_label: "Selektiertes Schlagwort entfernen",
  TaxonomyExplorerPanel_cut_button_label: "Schlagwort ausschneiden",
  TaxonomyExplorerPanel_delete_button_label: "Schlagwörter löschen",
  TaxonomyExplorerPanel_paste_button_label: "Schlagwort einfügen",
  TaxonomyExplorerColumn_emptyText_loading: "Lade Schlagworte...",
  TaxonomyExplorerColumn_emptyText_no_keywords: "Keine Schlagwörter gefunden",
  TaxonomyExplorerColumn_undefined: "nicht definiert",
  TaxonomyPreferences_option_name: "Vorschläge",
  TaxonomyPreferences_tab_title: "Schlagworte",
  TaxonomyPreferences_value_nameMatching_text: "Namentliche Übereinstimmung",
  TaxonomyPreferences_value_semantic_opencalais_text: "Semantische Auswertung (OpenCalais)",
  TaxonomyPreferences_settings_tooltip: "Die Vorschlag-Auswahl definiert die Art der Auswertung, die bei der Suche nach Schlagworten für ein Inhalt genutzt werden soll.",
  TaxonomySuggestions_empty_text: "Keine Vorschläge gefunden",
  TaxonomySuggestions_loading: "Lade Vorschläge",
  TaxonomyLinkList_add_suggestion_action_text: "Schlagwort hinzufügen",
  TaxonomyLinkList_edit_action_text: "In Schlagworten anzeigen",
  TaxonomyLinkList_empty_chooser_text: "Fügen Sie ein Schlüsselwort aus der unteren Liste hinzu",
  TaxonomyLinkList_keyword_remove_text: "Schlagwort entfernen",
  TaxonomyLinkList_singleSelection_title: "Ausgewältes Schlagwort",
  TaxonomyLinkList_status_loading_text: "Laden…",
  TaxonomyLinkList_suggestions_add_all: "Alle hinzufügen",
  TaxonomyLinkList_suggestions_reload: "Vorschläge neu laden",
  TaxonomyLinkList_suggestions_title: "Vorschläge",
  TaxonomyLinkList_title: "Ausgewählte Schlagworte",
  TaxonomyLinkList_contextMenu_chooseTag: "Schlagwort auswählen",
  TaxonomyTranslationFields_title: "Übersetzungen",
  TaxonomyTranslationFields_emptyText: "Geben Sie hier die Übersetzung ein.",
  Taxonomy_action_tooltip: "Schlagwort auswählen",
  taxonomy_selection_dialog_title: "Schlagwort auswählen",
  TaxonomyType_Location_text: "Orte Schlagworte",
  TaxonomyType_Subject_text: "Themen Schlagworte",
  TaxonomyChooser_selection_text: "Alle verfügbare Schlagworte durchsuchen",
  TaxonomyChooser_search_tag_title: "Suche",
  TaxonomyChooser_search_tag_emptyText: "Schlagwort suchen…",
  Favbar_taxonomies_button_label: "Schlagworte",
  Location: "Orte",
  Subject: "Themen",
  Taxonomy_l10n_title: "Titel ({0})",
});
