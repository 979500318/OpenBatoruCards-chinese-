package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.DownCost;

public final class ARTS_W_CrystalExplosion extends Card {

    public ARTS_W_CrystalExplosion()
    {
        setImageSets("WX24-P3-001", "WX24-P3-001U");

        setOriginalName("クリスタル・エクスプロージョン");
        setAltNames("クリスタルエクスプロージョン Kurisutaru Ekusupuroojon");
        setDescription("jp",
                "あなたのレベル２以上のセンタールリグ１体を対象とし、次のあなたのエナフェイズ終了時まで、それのリミットを＋１し、それは以下の能力を得る。" +
                "@>@U：あなたのアタックフェイズ開始時、このルリグをアップする。\n" +
                "@A $T1 #D：あなたのデッキの上からカードを４枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Crystal Explosion");
        setDescription("en",
                "Target your level 2 or higher center LRIG, and until the end of your next ener phase, it gets +1 limit, and it gains:" +
                "@>@U: At the beginning of your attack phase, up this LRIG.\n" +
                "@A $T1 #D: Look at the top 4 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "水晶·光芒四射");
        setDescription("zh_simplified", 
                "你的等级2以上的核心分身1只作为对象，直到下一个你的充能阶段结束时为止，其的界限+1，其得到以下的能力。\n" +
                "@>@U :你的攻击阶段开始时，这只分身竖直。\n" +
                "@A $T1 #D从你的牌组上面看4张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }

        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().LRIG().withLevel(2,0).getValidTargetsCount() == 0 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(2,0)).get();
            if(target == null) return;
            
            gainValue(target, target.getIndexedInstance().getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachAbility(target, attachedAuto, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachAbility(target, attachedAct, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            getAbility().getSourceCardIndex().getIndexedInstance().up();
        }
        private void onAttachedActionEff()
        {
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
