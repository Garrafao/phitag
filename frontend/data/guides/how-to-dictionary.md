---
id: 'how-to-dictionary'
title: 'How to: Dictionary'
priority: 10
description: "Learn how to use the Phitag's dictionary"
---

# How to: Dictionary

PhiTag allows users to create their own dictionaries, with an intuitive interface. Dictionaries will become more useful, as they get integrated into the annotation process. Currently, dictionaries can be used to view, edit and export the data they contain in a variety of formats.

## Creating a dictionary

To create a dictionary, click on the "Create Dictionary" button in the bottom right corner of the dictionary overview. This will open a modal, which allows you to specify the name and description of the dictionary. After you have specified the name and description, click on the "Create" button to create the dictionary. This will create a new and empty dictionary, which you can now select in the dictionary overview and start adding data to.

You can also create a dictionary by importing a file, which can be done by selecting a file and the format of the file in the modal. Currently, PhiTag supports importing dictionaries in the following formats:
- [Custom XML](/guides/explained-dictionary-formats)
- [Wiktionary XML (DE)](/guides/explained-dictionary-formats)
- [Wiktionary XML (EN)](/guides/explained-dictionary-formats)

If you want to import a dictionary in a different format, please let us know. If you can also provide a schema for the format (e.g. an XSD file), this will speed up the process of adding support for the format.

| ![Create Dictionary](/gif/guide/dictionary-create.gif) |
| :----------------------------------------------------: |
|                   Create Dictionary                    |

## Viewing a dictionary

After you have created a dictionary, you can view it by selecting it in the dictionary overview. This will open the dictionary view, which shows the data contained in the dictionary. The dictionary view consists of three parts:
- Search bar
- Entry list
- Entry view

### Search bar

The search bar allows you to search for entries in the dictionary by their headword and will filter the entry list accordingly, by showing entries that match the search query in their prefix (case-insensitive). To the right of the search bar, you will find two buttons; a help button, which will open this guide, and a button to export the dictionary.
The export button will open a modal, which allows you to select the format in which you want to export the dictionary, which are the same formats as the ones supported for importing dictionaries. After you have selected the format, click on the "Export" button to export the dictionary. This will download a file containing the dictionary in the selected format.

### Entry list

The entry list shows the first seven entries in the dictionary, which match the search query. If there are more than seven entries, you can use the pagination buttons at the bottom of the entry list to navigate through the entries. Each entry in the entry list shows the headword of the entry, as well as the part of speech of the entry. Clicking on an entry in the entry list will open the entry view for that entry.

### Entry view

The entry view shows the data of the entry that is currently selected in the entry list. The entry view consists of three parts:
- Headword
- Part of speech
- Sense list
- Example list

The headword of the entry is shown at the top of the entry view. The headword is the word that the entry describes. The headword is shown in bold, followed by the part of speech of the entry as subscript and in grey. Below the headword, you will find the sense list, which shows the senses of the entry. Each sense in the sense list shows the definition of the sense, as well as a list of examples.

## Editing a dictionary

To edit an entry in a dictionary, hover over the entry in the entry list and click on the edit button that appears on the right side of the entry. This will change the entry view into edit mode, which allows you to edit the selected data. By pressing on the *checkmark* button to the right of the edited data, you can save the changes you have made. By pressing on the *cross* button to the right of the edited data, you can discard the changes you have made. 

### Deleting a dictionary entry, sense or example

If you want to delete certain data from an entry, you can do so by hovering over the data you want to delete and clicking on the delete button that appears on the right side of the data. This will delete the data from the entry. If you want to delete an entire entry, you can do so by hovering over the headword of the entry and clicking on the delete button that appears on the right side of the entry. This will delete the whole entry from the dictionary.

| ![Edit Dictionary](/gif/guide/dictionary-edit.gif) |
| :------------------------------------------------: |
|                  Edit Dictionary                   |

## Adding a dictionary entry, sense or example

To add a new entry, you can click the bottom right button on the page. This will open a modal, which allows you to specify the headword and part of speech of the entry. After you have specified the headword and part of speech, click on the "Create" button to create the entry. This will create a new and empty entry, which you can now edit and add data to.

To add a new sense to an entry, you can click the *plus* button, which appears when you hover over the headword of the entry. This will add a new sense to the entry, which you can now edit and add data to.

To add a new example to a sense, you can click the *plus* button, which appears when you hover over the sense. This will add a new example to the sense, which you can now edit and add data to.

| ![Add Dictionary](/gif/guide/dictionary-new-entry.gif) |
| :----------------------------------------------------: |
|                  Add Dictionary Entry                  |

## Exporting a dictionary

If you want to export a dictionary, you can do so by clicking on the export button in the search bar. This will open a modal, which allows you to select the format in which you want to export the dictionary, which are the same formats as the ones supported for importing dictionaries. After you have selected the format, click on the "Export" button to export the dictionary. This will download a file containing the dictionary in the selected format.

| ![Export Dictionary](/gif/guide/dictionary-export.gif) |
| :----------------------------------------------------: |
|                Export Dictionary Entry                 |



## Introduction by Example

<video width="100%" controls>
  <source src="/video/dictionary-example.mp4" type="video/mp4">
</video>