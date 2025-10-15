package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_CodeArtGRabo extends Card {

    public SIGNI_W1_CodeArtGRabo()
    {
        setImageSets("WXDi-P09-050");

        setOriginalName("コードアート　Gラボ");
        setAltNames("コードアートジーラボ Koodo Aato Jii Rabo");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚見る。その中からカード１枚をデッキの一番上に戻し、残りを好きな順番でデッキの一番下に置く。\n" +
                "@U：あなたのターン終了時、このシグニがアップ状態の場合、あなたのデッキの一番上を公開してもよい。そのカードがスペルの場合、カードを１枚引く。"
        );

        setName("en", "G-Lab, Code: Art");
        setDescription("en",
                "@E: Look at the top two cards of your deck. Put a card on top of your deck, and put the rest on the bottom of your deck in any order.\n" +
                "@U: At the end of your turn, if this SIGNI is upped, you may reveal the top card of your deck. If that card is a spell, draw a card."
        );
        
        setName("en_fan", "Code Art G Rabo");
        setDescription("en_fan",
                "@E: Look at the top 2 cards of your deck. Return 1 of them to the top of your deck, and put the rest on the bottom of your deck in any order.\n" +
                "@U: At the end of your turn, if this SIGNI is upped, you may reveal the top card of your deck. If it is a spell card, draw 1 card."
        );

		setName("zh_simplified", "必杀代号 润滑剂");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看2张牌。从中把1张牌返回牌组最上面，剩下的任意顺序放置到牌组最下面。\n" +
                "@U :你的回合结束时，这只精灵在竖直状态的场合，可以把你的牌组最上面公开。那张牌是魔法的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private void onEnterEff()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate())
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex == null || cardIndex.getIndexedInstance().getTypeByRef() != CardType.SPELL ||
                   draw(1).get() == null)
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
