---
id: 'introduction'
title: 'Introduction to Phitag'
priority: 100
description: 'This guide will give you the core idea of Phitag.'
---

# Introduction

## What is PhiTag?

PhiTag is an web-based annotation tool for text annotation. It is designed to be modular and extensible, so that it can be adapted to a variety of annotation tasks, which is also why it is [open source](https://github.com/Garrafao/phitag), allowing anybody to contribute to its development. It is also designed to be locally installable, so that it can be used in any environment. If you are interested in new features, please reach out to [us](https://www.ims.uni-stuttgart.de/en/institute/team/Schlechtweg/), or contribute to the project yourself by opening a pull request.


## Text Annotation

This platform supports a variety of text annotation tasks, which can be combined to create complex annotation projects. See the [Supported Tasks](/guide/supported-tasks) guide for an overview of the currently supported tasks and their corresponding data formats. As we have the "open source" idea in mind, we use a standardized data format for all tasks, which is also open source and can be found [here](https://github.com/ChangeIsKey/annotation_standardization). This allows us to easily add new tasks to the platform, as long as they are supported by the data format. By using a standardized data format, we also allow users to define their own tasks, which then can be added to the platform. Consider contributing to the [standardization repository](https://github.com/ChangeIsKey/annotation_standardization) if you have any ideas for new tasks or data formats.

## Annotation Process

PhiTag is designed with the annotation process in mind, which is why it provides a variety of tools to help you organize your annotation project and annotators. The platform allows you to create projects, which can be used to organize your annotation tasks. Each project can contain multiple tasks/phases, which can be annotated by multiple annotators of your choice. The platform also provides tools for annotator training and annotation quality control. See the [How to: Annotation Manager](/guide/how-to-annotation-manager) guide for more information on how to use PhiTag to organize your annotation project. Furthermore, the platform provides a variety of tools to help you with the annotation process itself, by simplifying the annotation interface such that it is intuitive and easy to use. If you are interested in how to use the platform as an annotator, see the [How to: Annotator](/guide/how-to-annotator) guide.

|         ![](/gif/guide/project-create.gif)         |       ![](/gif/guide/add-phase.gif)       | ![](/gif/guide/add-annotator.gif) | ![](/gif/guide/annotate-usepair.gif)  |
| :------------------------------------------------: | :---------------------------------------: | :-------------------------------: | :-----------------------------------: |
| Create a project to organize your annotation tasks | Add a new annotation task to your project |  Add annotators to your project   | Annotate a text with the usepair task |

## Computational Annotation

In cases where the annotation process is too time consuming or expensive, PhiTag also provides tools for computational annotation. This allows you to use computational linguistic models to automate the annotation process as far as possible, which can then be used to scale up the annotation process. See the [How to: Annotation Manager](/guide/how-to-annotation-manager) guide for more information on how to use PhiTag for computational annotation.

|          ![](/gif/guide/com-add.gif)          | ![](/gif/guide/com-annotation-start.gif) | ![](/gif/guide/com-annotation-end.gif) | ![](/gif/guide/com-instances.gif) |
| :-------------------------------------------: | :--------------------------------------: | :------------------------------------: | :-------------------------------: |
| Add a computational annotator to your project |       Start the annotation process       |      Annotation process completed      |        Generate instances         |

## Use Case Specialization

PhiTag is designed to be modular and extensible, so that it can be adapted to a variety of annotation tasks. However, this also means that it is not specialized for any specific use case. To solve this problem, the platform implements an abstraction layer, which allows us to specialize the platform for specific use cases. This abstraction layer abstracts heavily over the underlying data structures, tasks and types to provide meta level functionality, which can then be used to solve specific use cases. See the [How to: Lexicography](/guide/how-to-lexicography) guide for an example of how this abstraction layer can be used to solve the use case of lexicography. If you are interested in using PhiTag for your specific use case, please reach out to [us](https://www.ims.uni-stuttgart.de/en/institute/team/Schlechtweg/).

## Introduction by Example

<video width="100%" controls>
  <source src="/video/introduction.mp4" type="video/mp4">
</video>