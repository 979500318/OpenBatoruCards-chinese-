package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K4_DarkAliceSuperTrap extends Card {

    public SIGNI_K4_DarkAliceSuperTrap()
    {
        setImageSets("WDK04-011");

        setOriginalName("超罠　ダークアリス");
        setAltNames("チョウビンダークアリス Choubin Daaku Arisu");
        setDescription("jp",
                "@A $T1 %K：あなたのデッキの一番上を公開する。その後、そのカードがレベルが偶数のシグニの場合、そのカードを手札に加える。レベルが奇数のシグニの場合、そのカードをトラッシュに置き、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Dark Alice, Super Trap");
        setDescription("en",
                "@A $T1 %K: Reveal the top card of your deck. If it is a SIGNI with an even level, add it to your hand. If it is a SIGNI with an odd level, put it into the trash, and target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

		setName("zh_simplified", "超罠 黑暗爱丽丝");
        setDescription("zh_simplified", 
                "@A $T1 %K:你的牌组最上面公开。然后，那张牌是等级在偶数的精灵的场合，那张牌加入手牌。等级在奇数的精灵的场合，那张牌放置到废弃区，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    if(cardIndex.getIndexedInstance().getLevelByRef() % 2 == 0)
                    {
                        addToHand(cardIndex);
                    } else {
                        trash(cardIndex);

                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                        gainPower(target, -8000, ChronoDuration.turnEnd());
                    }
                }
                
                if(cardIndex.getLocation() == CardLocation.REVEALED) returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
