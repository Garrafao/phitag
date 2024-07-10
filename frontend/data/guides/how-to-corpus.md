---
id: 'how-to-corpus'
title: 'How to: Corpus'
priority: 10
description: "Learn how to use the Phitag's corpus to generate data for your annotation project."
---

# How to: Corpus

PhiTag provides users with an in-built [corpus](/corpus), a collection of texts, searchable by different criteria and exportable to your annotation project. The corpus is a useful tool for finding texts to annotate, and for generating data for your annotation project. 

## Searching the corpus

Initially, after accessing the corpus search, you will be presented with an empty page and a search bar. This search bar allows you to search all corpora for a specific lemma. After you type in a search query, the search bar will present you with a list of lemmas that match your query. You can select one of these lemmas to search for it in the corpus. 

| ![](/gif/guide/corpus-search.gif) |
| :-------------------------------: |
|            Search Bar             |

## Extended search

If the simple search does not fit your needs, you can use the extended search, which can be found next to the search bar. The extended search allows you to specify further criteria for your search:

- Part of speech (POS)
- Date range (from - to)
- Normalized Version 
- Context (left and right, if applicable)

We are working on expanding the extended search to include more criteria and if you have any suggestions, please let us know.

| ![](/gif/guide/corpus-extended.gif) |
| :---------------------------------: |
|           Extended Search           |

## Search results

After you have searched for a lemma, you will be presented with a list of results. Each result is presented as follows:

- Text containing the lemma
- The title of the resource the text is from
- The author of the resource
- The language of the resource
- The date of the resource
- The year of the resource
- The URL of the resource
- A select button, which allows you to select the text for export

Note, the text is either normalized or original and with or without context, depending on your search criteria. Normally, the text is in its original form without context. The lemma you searched for will be highlighted in the text green and the sentence containing the lemma will be bolded.

## Exporting texts

After you have selected the texts you want to export, you can click on the export button in the bottom right corner. This will open a modal, which allows you to select the project you want to export the texts to. After you have selected the project, you can click on the export button to export the texts. The texts will be added to the project as usages (i.e. text annotations), see [Supported Tasks](/guides/supported-tasks) for more information.

| ![](/gif/guide/corpus-usages.gif) |
| :-------------------------------: |
|         Export to Project         |

## Limitations

The corpus is still in development and there are some limitations to its functionality. Currently, the corpus only holds a small subset of the [German Text Archive (DTA)](https://www.deutschestextarchiv.de/), but we are working on expanding it. If you have any suggestions for texts to add, please let us know.

## Introduction by Example

<video width="100%" controls>
  <source src="/video/corpus-example.mp4" type="video/mp4">
</video>