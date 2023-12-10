# Figure Drawing Application

This application provides a platform for creating, manipulating, and organizing figures effectively. Below are the main features and functionalities:

## Key Features:

### 1. Drawing and Selection

- **Multiple Selection:** Draw a rectangle to select all figures within it.
- **Grouping and Ungrouping:** Create groups of selected figures and ungroup them as needed. Each group will act as an individual figure. You are allowed to group multiple groups as well as ungrouping multiple selected groups simultaneously.

### 2. Effects

- **Beveling:** Apply a bevel effect to the figures.
- **Gradient:** Add a gradient effect to the figures.
- **Shadow:** Incorporate shadows to the figures.

Note: You can add an effect to a figure and group it with another one that has no effects at all. In such a case, an appropiate symbol will be shown in the effect's checkbox.

### 3. Transformation Actions

- **Rotation:** Rotate a selected figure by 90 degrees.
- **Flip:** Flip the selected figure horizontally and vertically.
- **Scaling and Descaling:** Adjust the size of the figure as needed with 25% adjustments.

Note: You can apply these transformations either to already formed groups of figures or to multiple selected figures.

### 4. Labels

- **Add Labels:** Assign labels to each group of figures.
- **Label Filtering:** Filter and display only the figures with a specific label.

Note: Each figure can have its own label, even when groupped with other figures with differents labels. However, when groupped, the whole set will be visible when filtering by a specific label, even if there is an only figure in the group with it. When ungroupping, filtering by the same label will show the only figure that had been tagged, as groupping does not assign every label present in the group to every figure in it. 
