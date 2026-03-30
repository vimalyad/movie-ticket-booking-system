Relationship between Entities:

1. Inheritance 
2. Association : Student and Teacher , they interact with each other, but they do not own each other.
- single line
3. Aggregation: Songs and Playlist , one lives in other but on deleting bigger entity smaller entity doesn't get's deleted.
- empty diamond on bigger entity
4. Composition: Order and OrderItems , If order gets deleted then order items become orphan, and they have to be deleted
- filled diamond on bigger entity