---
id: 'explained-annotation-task-span'
title: 'Explained: Annotation Task - Text Span'
priority: 20
description: 'Explanation of the Text Span Annotation Task.'
---

# Text Span Annotation

## Introduction
Your task is to annotate the part of speech of each word in a text snippet by marking the word and selecting the right tag from the following set:


| ![](/image/span.png)     | 
| :-----------------------------------: | 
|          "Universal Dependencies" Tagset |  

## Data Format
Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guide/supported-tasks) guide.
### instances.tsv
**instanceIDs**:  A unique ID for the instance.\
**dataIDs**: A dataID corresponding to a text to be annotated.\
**label_set**: A set of labels for ex:(Noun, Pronoun, Verb, Adjective).

### judgments.tsv
**label**: A single value from the **label_set** or '-' for non-label.

## Annotation Process


Consider the following text:

|Text |
|--------|
|This veteran , the oldest pensioner in Great Britain , entered the army in 1758 , and was severely wounded at the battle of Quebec under General Wolfe , in consequence of which he became an out-pensioner of Chelsea Hospital , and continued so for the period of seventy-five years.|

You could start out by marking the first word 'This' and select the label 'DET' for determiner and continue by selecting the word 'veteran' and select the label 'NOUN'.

## Visual Example

| ![](/gif/guide/span-annotate.gif)     | 
| :-----------------------------------: | 
|    Annotating  text span task         |  
