---
id: 'supported-tasks'
title: 'Supported Tasks'
priority: 50
description: "Current overview of Phitag's supported annotation tasks."
---

# Supported Tasks

PhiTag is an extensible, flexible and modular annotation tool for text annotation. It supports a variety of annotation tasks, which can be combined to create complex annotation projects. This page provides an overview of the currently supported tasks and their corresponding data formats.

## Data Formats

The following sections will describe the currently supported data formats.

## Usage (text snippets)

Usages represent the most basic building block of each annotation project. They consist primarly of text snippets, from which the annotator selects a part for a given annotation task/phase. Usages are uploaded in the following TSV format:

```tsv
dataID	context	indices_target_token	indices_target_sentence	lemma   group
1	"Der Hund bellt."	[4, 8]	[0, 17]	bellen  Example
```

The columns are as follows:

- `dataID`: A unique identifier for each usage. This is used to identify the usage in the annotation project.
- `context`: The text snippet, which is presented to the annotator. Note, that the context can be a sentence, a paragraph or a whole text, depending on the annotation task.
- `indices_target_token`: The indices of the target token in the context. This is used to highlight the target token in the context and in most cases will be highlighted in green.
- `indices_target_sentence`: The indices of the target sentence in the context. This is used to highlight the target sentence in the context and in most cases will be bolded.
- `lemma`: The lemma of the target token. This is used to preselect the target token in the annotation task.
- `group`: An optional colum, currently not used.

**Note**: The attributes **dataId** and **context** are mandatory, while the remaining attributes are optional.
## Instance

Instances represent a selection of usages (and possibly other instances) for a given annotation task/phase. Each instance, as the name suggests, is an instance of an annotation task and hence the structure depends heavily on the annotation task. Please refer to the annotation task type down below for more information.

## Judgements

Judgements represent the annotations of a given instance. Each judgement is an annotation of an instance and will in most cases follow the following format:

```tsv
instanceID	label	comment	annotator
1	"Example"	"Example comment"	"Example annotator"
```

The columns are as follows:

- `instanceID`: The unique identifier of the instance, which was annotated.
- `label`: The label of the annotation. This is used to identify the annotation in the annotation project and corresponds to either the label or non-label defined in an instance.
- `comment`: An optional comment, which can be added to the annotation.
- `annotator`: The annotator, who created the annotation.

# Annotation Type

In the following we will describe the currently supported annotation tasks. Note, that even though the implemented tasks here are described in a specific way, the annotation tool is flexible enough to support variations of these tasks. If you have a specific task in mind, which is not achievable with the current implementation, please contact us!

## Text Pair

The text pair annotation type is used to annotate the relation between two usages. It is used to annotate the relation between two usages. We currently support the annotation task "Text Pair Relatedness" which asks annotators to rate the degree of semantic relatedness between two uses of a lemma. For further information please refer to the ['Explained: Annotation Task - Text Pair Relatedness' guide](/guide/explained-annotation-task-urel).

## Text Single

The text single annotation type is the counterpart to the text pair annotation type. It is used to annotate a single usage. We currently support to tasks for this annotation type; "Text Label", "Text Free", "Text Sentiment" and "Text Choice". Text Label asks annotators to rate the degree of a sense, which may or may not apply to the target lemma, on a given scale (e.g. 1-5). Text Free asks annotators to provide a substitution for the target lemma. Text Sentiment ask annotators asks annotator to rate the emotional sentiment as "positive", "neutral", and "negative". For further information please refer to the ['Explained: Annotation Task - Text Label' guide](/guide/explained-annotation-task-wssim), the ['Explained: Annotation Task - Text Free Subsitution' guide](/guide/explained-annotation-task-lexsub) and the ['Explained: Annotation Task - Text Sentiment' guide](/guide/explained-annotation-task-sentiment).
## Text Rank

The text rank annotation task asks annotator to rank a set of usages according to some property. This property should be specified in the guidelines and could be e.g. ambiguity or sentiment. We currently support to tasks for this annotation type; "Text Rank", "Text Rank Pair" and "Text Rank Relative". For further information please refer to the ['Explained: Annotation Task - Text Rank' guide](/guide/explained-annotation-task-userank), ['Explained: Annotation Task - Text Rank Relative' guide](/guide/explained-annotation-task-userankrelative) and the ['Explained: Annotation Task - Text Rank Pair' guide](/guide/explained-annotation-task-userankpair).

## Text Span
The text span task is to annotate the part of speech of each word in a text snippet by marking the word and selecting the right tag from the given set.
Please refer this guideline for further information. ['Explained: Annotation Task - Text Span' guide](/guide/explained-annotation-task-span) 


