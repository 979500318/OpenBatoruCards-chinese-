package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.ReturnToDeckCost;

public final class SIGNI_B1_CodeMazeUmrMemoria extends Card {
    
    public SIGNI_B1_CodeMazeUmrMemoria()
    {
        setImageSets("WXDi-P08-062", "WXDi-P08-062P");
        
        setOriginalName("コードメイズ　ウムル//メモリア");
        setAltNames("コードメイズウムルメモリア Koodo Meizu Umuru Memoria");
        setDescription("jp",
                "@E：あなたのデッキの一番上を見る。そのカードをデッキの一番下に置いてもよい。\n" +
                "@A @[このシグニを場からデッキの一番下に置く]@：あなたのデッキの一番上を公開する。そのカードが《コードメイズ ウムル//メモリア》以外のシグニの場合、そのシグニを場に出してもよい。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Umr//Memoria, Code: Maze");
        setDescription("en",
                "@E: Look at the top card of your deck. You may put that card on the bottom of your deck.\n" +
                "@A @[Put this SIGNI on your field onto the bottom of its owner's deck]@: Reveal the top card of your deck. If it is a SIGNI other than \"Umr//Memoria, Code: Maze\", you may put it onto your field." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Maze Umr//Memoria");
        setDescription("en_fan",
                "@E: Look at the top card of your deck. You may put it on the bottom of your deck.\n" +
                "@A @[Put this SIGNI from the field on the bottom of your deck]@: Reveal the top card of your deck. If that card is a SIGNI other than \"Code Maze Umr//Memoria\", you may put it onto the field." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "迷宫代号 乌姆尔//回忆");
        setDescription("zh_simplified", 
                "@E :看你的牌组最上面。可以把那张牌放置到牌组最下面。\n" +
                "@A 这只精灵从场上放置到牌组最下面:你的牌组最上面公开。那张牌是《コードメイズ　ウムル//メモリア》以外的精灵的场合，可以把那张精灵出场。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new ReturnToDeckCost(DeckPosition.BOTTOM), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = look();
            
            returnToDeck(cardIndex, cardIndex != null && playerChoiceActivate() ? DeckPosition.BOTTOM : DeckPosition.TOP);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = reveal();
            
            if(!CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) ||
               cardIndex.getIndexedInstance().getName().getValue().contains("コードメイズ　ウムル//メモリア") ||
               !playerChoiceActivate() || !putOnField(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
