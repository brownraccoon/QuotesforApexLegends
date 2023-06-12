package com.br.quotesforapexlegends.repository

class FirebaseRepository {
        private val databaseReference: DatabaseReference

        init {
            val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
            // Assuming your Firebase Realtime Database root reference is "data"
            databaseReference = firebaseDatabase.getReference("data")
        }

        fun fetchData(callback: FirebaseCallback) {
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Process the data snapshot and convert it into the desired format
                    // For example, if your data is a list of strings
                    val dataList = ArrayList<String>()
                    for (childSnapshot in dataSnapshot.children) {
                        val data = childSnapshot.getValue(String::class.java)
                        data?.let {
                            dataList.add(it)
                        }
                    }

                    // Pass the fetched data to the callback
                    callback.onDataReceived(dataList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during data fetching
                    callback.onError(databaseError.message)
                }
            })
        }

        interface FirebaseCallback {
            fun onDataReceived(dataList: List<String>)
            fun onError(errorMessage: String)
        }


}