---
id: 'explained-annotation-task-urel'
title: 'Explained: Annotation Task - Usage Relatedness (UREL)'
priority: 20
description: 'Explanation of the Usage Relatedness (UREL) Annotation Task.'
---

# Usage Relatedness (UREL) Annotation Task

The usage relatedness annotation tasks asks annotators to rate the degree of semantic relatedness between two uses of a word.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guides/supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A pair of dataIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same.

**label_set**: A scale, e.g. (1,2,3,4).

**non_label**: A non-label (-).

## Annotation Process

Your task is to rate the degree of semantic relatedness between two uses of a word. For instance, presented with a sentence pair as in the below table, you are asked to rate the semantic relatedness between the two uses of **grasp**.

|Usage 1 | Usage 2 |
|--------|---------|
|He continued to **grasp**, between forefinger and thumb, the edge of the cloth I had been sewing.|For just a moment he didn't **grasp** the import of what the old man had said.|

### Task Structure

You will be shown two sentences displayed next to each other, as you can see in Example A. The target word is marked in green in the respective sentence, which is marked bold. Your task is to evaluate, for each of these pairs of sentences, how strong the semantic relatedness is between the two uses of the target word in the two sentences.

Because language is often ambiguous, it is important that you first read each sentence in a sentence pair individually and decide on the most plausible meaning of the target word BEFORE comparing the two uses of the word. In some cases, the sentences already provide enough information to understand the meaning of the target word; however, for cases that are unclear, you can find additional context beyond that in gray.

*** 
Please try to ignore differences between the uses that do not impact their meaning. For example, eat and ate can express the same meaning, even though one is in present tense, and the other is in past tense. Also, distinctions between singular and plural (as in carrot vs. carrots) are typically irrelevant for the meaning.

Note that there are no right or wrong answers in this task, so please provide your subjective opinion. However, please try to be consistent in your judgments.
|Usage 1 | Usage 2 |
|--------|---------|
|Speaking of bread and butter reminds me that we'd better **eat** ours before the coffee gets cold.|When the meal was over and they had finished their tea after they **ate**, wang the Second took the trusty man to his elder brother's gate.|

[**4-Identical**, 3-Closely Related, 2-Distantly Related, 1-Unrelated]

Example A: Judgment 4 (Identical)
***
## Annotation Examples
We now consider some evaluation examples with a judgement scale ranging from 1 to 4 to illustrate the different degrees of semantic relatedness you might encounter in annotation. Please note, as mentioned above, that these are only examples, and you should always give your subjective opinion.

The two instances of eat in **Example A** are judged identical in meaning (rating: 4), because both uses refer to the physical act of consuming food.

In contrast, the two uses of child in **Example B** are judged closely related but not identical (rating: 3), because the meaning of child(ren) in sentence 1 may be paraphrased as ‘young person’, while the meaning in sentence 2 is closer to ‘offspring’.

|Usage 1| Usage 2|
|--------|---------|
|He agreed and began practicing his sleight of hand tricks to the great pleasure of some **children**, the same ones, I suspect, who had plagued me when I was a child| The daylight had long faded; her **child** lay calmly sleeping by her side; a candle was burning dimly on the stand. |

[4-Identical, **3-Closely Related**, 2-Distantly Related, 1-Unrelated]

Example B: Judgment 3 (Closely Related)

***
In **Example C**, the two uses of the word crossroad art related, but more distantly (rating: 2): Unlike the child example above, the two uses of crossroad in this example have different meanings, which are yet related by a figurative similarity, on the level that both involve some kind of decision.

|Usage 1 | Usage 2 |
|--------|---------|
|He came to a **crossroad** and read the signs; to the south, Kenniston, 20 m.|As a result we are at a **crossroad**; either school integration efforts will be abandoned in the South, or they will be pursued in the North as well.|

[4-Identical, 3-Closely Related, 2-Distantly Related, 1-Unrelated]

Example C: Judgment 2 (Distantly Related)


***
A rating of 1 is used for two uses of a word that are completely unrelated in their meaning, as it is the case for bank in **Example D**. Note that this pair of uses is semantically more distant than the two uses of crossroad above. River banks and financial banks are not semantically related to each other.

|Usage 1 | Usage 2 |
|--------|---------|
|His parents had left a lot of money in the **bank** and now it was all Measle's, but a judge had said that Measle was too young to get it|Sherrell, is is said, was sitting on the **bank** of the river close by, and as soon as the men had disappeared from sight he jumped on board the schooner.|

[4-Identical, 3-Closely Related, 2-Distantly Related, **1-Unrelated**]

Example D: Judgment 1 (Unrelated)

***

Finally, the non_label symbol '-' is used when the annotator is unable to make a judgment. Please use this option only if absolutely necessary, i.e., if you cannot make a decision about the degree of semantic relatedness between the two words marked in bold. This may be the case, for example, if you find a sentence too flawed to understand, the use of the target word is ambiguous, or the two uses of the target word do not match (i.e., do not have the same lemma). In **Example E**, the annotator is unable to judge semantic relatedness because Usage 1 is ambiguous; it is unclear whether the word "bank" refers to a financial institution or the bank of a river.

|Usage 1 | Usage 2 |
|--------|---------|
|I saw Mark as he was heading down to the **bank**.|Sherrell, is is said, was sitting on the **bank** of the river close by, and as soon as the men had disappeared from sight he jumped on board the schooner.|

[4-Identical, 3-Closely Related, 2-Distantly Related, 1-Unrelated]

Example E: Judgment '-' (non_label)

## Visual Example

![Annotation Example](/gif/guide/annotate-usepair.gif)