package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass.CardSIGNIClassValue;

import java.util.ArrayList;
import java.util.List;

public final class LRIGA_K1_AssistMitoTsukinoLevel1Sin extends Card {

    public LRIGA_K1_AssistMitoTsukinoLevel1Sin()
    {
        setImageSets("WXDi-CP01-020");

        setOriginalName("【アシスト】月ノ美兎　レベル１【罪】");
        setAltNames("アシストツキノミトレベルイチツミ Ashisuto Tsukino Mito Reberu Ichi Tsumi Assist Mito Assist Tsukino");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから#Gを持たない、共通するクラスを持つシグニ２枚を対象とし、それらを手札に加える。"
        );

        setName("en", "[Assist] Mito, Level 1 [Sin]");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, add two target SIGNI without a #G that share a class from your trash to your hand."
        );
        
        setName("en_fan", "[Assist] Mito Tsukino Level 1 [Sin]");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, target 2 SIGNI without #G @[Guard]@ that share a common class from your trash, and add them to your hand."
        );

		setName("zh_simplified", "【支援】月之美兔 等级1【罪】");
        setDescription("zh_simplified", 
                "@E 从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把不持有#G，持有共通类别的精灵2张作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private boolean found;
        private void onEnterEff()
        {
            millDeck(2);

            found = false;
            List<CardSIGNIClassValue> cacheSIGNIClasses = new ArrayList<>();
            forEachCardInTrash(getOwner(), cardIndex -> {
                if(found || cardIndex.getIndexedInstance().isState(CardStateFlag.CAN_GUARD)) return;

                for(CardSIGNIClassValue cardSIGNIClassValue : cardIndex.getIndexedInstance().getSIGNIClass().getValue())
                {
                    if(cacheSIGNIClasses.contains(cardSIGNIClassValue))
                    {
                        found = true;
                        return;
                    } else {
                        cacheSIGNIClasses.add(cardSIGNIClassValue);
                    }
                }
            });
            
            if(found)
            {
                DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash(), this::onEnterEffTargetCond);
                addToHand(data);
            }
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 && listPickedCards.get(0).getIndexedInstance().getSIGNIClass().matches(listPickedCards.get(1).getIndexedInstance().getSIGNIClass());
        }
    }
}
