package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W1_YoshimiIbaragi extends Card {

    public SIGNI_W1_YoshimiIbaragi()
    {
        setImageSets("WX25-CP1-054");

        setOriginalName("伊原木ヨシミ");
        setAltNames("イバラギヨシミ Ibaragi Yoshimi");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのデッキの一番上を公開する。その後、そのカードが＜ブルアカ＞の場合、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。@@" +
                "~#あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Ibaragi Yoshimi");

        setName("en_fan", "Yoshimi Ibaragi");
        setDescription("en",
                "@U: At the end of your turn, reveal the top card of your deck. Then, if it is a <<Blue Archive>> card, target 1 of your opponent's SIGNI, and put it into the trash." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand.@@" +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "伊原木好美");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的牌组最上面公开。然后，那张牌是<<ブルアカ>>的场合，对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                    trash(target);
                }
                
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().guard().fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(reveal(cardIndex))
            {
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
