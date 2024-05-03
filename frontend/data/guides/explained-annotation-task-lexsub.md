---
id: 'explained-annotation-task-lexsub'
title: 'Explained: Annotation Task - Lexical Substitution (LEXSUB)'
priority: 20
description: 'Explanation of the Lexical Substitution (LEXSUB) Annotation Task.'
---

# Lexical Substitution (LEXSUB) Annotation Task

In the LEXSUB annotation task, annotators are presented a series of sentences, each containing a target lemma. Users are asked to provide a synonym to replace the target lemma in the context of the sentence in which it appears. The substitute word should preserve the meaning of the target as closely as possible.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guides/supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A data ID corresponding to the the dataID in uses and a senseID from the senses.tsv file.

**label_set**: Empty label set

**non_label**: Empty non-label

## Annotation Process
Please note that there is no right or wrong answer. We just want your opinion for each item on this task.

During the course of this experiment, you will be presented with a series of sentences near the top of your screen. Each sentence has one word that is the `test word'. We would like you to find a substitute for the test word that preserves the meaning of the word as it is used in the sentence as much as possible.

### Annotation Examples

|Greer Garson was a world famous **star**. |
|------------------------------------------|
|Substitute: **Actress**                   |

You may also put a substitute that is close in meaning, even though it doesn't preserve the meaning. In such cases, please aim for a word as close as possible to the meaning of the test word, and preferably one more general than the target word. Please ONLY use a more general word if you really can't find a substitute that is a near match for the target word in meaning.

For example:

|The teacher drew **stars** on the board. |
|------------------------------------------|
|Substitute: **Shape**                   |

Sometimes if you really cannot think of a substitute, you may use the non-label '-' response.

## Visual Example

![Annotation Example](/gif/guide/annotate-lexsub.gif)