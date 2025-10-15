package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class SIGNI_W3_TsurumaruGreatEquipment extends Card {

    public SIGNI_W3_TsurumaruGreatEquipment()
    {
        setImageSets("WX24-P2-049", "SPDi02-28");

        setOriginalName("大装　ツルマル");
        setAltNames("タイソウツルマル Taisou Tsurumaru");
        setDescription("jp",
                "@C：【シュート】\n" +
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーをそのバニッシュしたシグニのパワーと同じだけ＋（プラス）する。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Tsurumaru, Great Equipment");
        setDescription("en",
                "@C: [[Shoot]]\n" +
                "@U: Whenever this SIGNI banishes a SIGNI in battle, target 1 of your white SIGNI, and until the end of your opponent's next turn, it gets + (plus) equal to the power of the banished SIGNI.\n" +
                "@E %W: Look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "大装 鹤丸国永");
        setDescription("zh_simplified", 
                "@C :[[击落]]\n" +
                "@U :当这只精灵因为战斗把精灵1只破坏时，你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+（加号）与那只破坏的精灵的力量相同的数值。\n" +
                "@E %W:从你的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityShoot());

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.WHITE)).get();
            if(target != null)
            {
                gainPower(target, getEvent().getCaller().getPower().getValue(), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
