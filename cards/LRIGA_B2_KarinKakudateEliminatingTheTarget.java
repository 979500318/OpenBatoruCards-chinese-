package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B2_KarinKakudateEliminatingTheTarget extends Card {

    public LRIGA_B2_KarinKakudateEliminatingTheTarget()
    {
        setImageSets("WXDi-CP02-035");

        setOriginalName("角楯カリン[ターゲット、排除する]");
        setAltNames("カクダテカリンターゲットハイジョスル Kakudate Karin Taagetto Haijosuru");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。それのパワーが15000以上の場合、対戦相手は手札を１枚捨てる。" +
                "~{{E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Kakudate Karin [Target Removal]");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field on the bottom of its owner's deck. If its power is 15000 or more, your opponent discards a card.\n~{{E @[Discard a <<Blue Archive>> card]@: Draw two cards."
        );
        
        setName("en_fan", "Karin Kakudate [Eliminating the Target]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and put it on the bottom of their deck. If its power is 15000 or more, your opponent discards 1 card from their hand." +
                "~{{E @[Discard 1 <<Blue Archive>> card from your hand]@: Draw 2 cards."
        );

		setName("zh_simplified", "角楯花凛[目标，排除]");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其放置到牌组最下面。其的力量在15000以上的场合，对战对手把手牌1张舍弃。\n" +
                "~{{E从手牌把<<ブルアカ>>牌1张舍弃:抽2张牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CC);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null)
            {
                Double power = target.getIndexedInstance().getPower().getValue();
                returnToDeck(target, DeckPosition.BOTTOM);
                
                if(power >= 15000)
                {
                    discard(getOpponent(), 1);
                }
            }
        }

        private void onEnterEff2()
        {
            draw(2);
        }
    }
}

