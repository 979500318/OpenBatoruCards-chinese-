package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CrushCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIG_R3_YuzukiThreeFlamingSwimmingFlower extends Card {

    public LRIG_R3_YuzukiThreeFlamingSwimmingFlower()
    {
        setImageSets("WXDi-P14-006", "WXDi-P14-006U");
        setLinkedImageSets("WXDi-P14-TK01","WXDi-P14-TK02","WXDi-P14-TK03","WXDi-P14-TK04","WXDi-P14-TK05");

        setOriginalName("炎泳華　遊月・燦");
        setAltNames("エンエイカユヅキサン Eneika Yuzuki San");
        setDescription("jp",
                "@E @[ライフクロス１枚をクラッシュする]@：対戦相手のライフクロス１枚をトラッシュに置く。\n" +
                "@A $T1 %R %X：あなたのライフクロスが２枚以下の場合、あなたの赤のシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。\n" +
                "@A @[エクシード４]@：フェゾーネマジックのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Yuzuki Brilliant, Surflame Beauty");
        setDescription("en",
                "@E @[Crush one of your Life Cloth]@: Put one of your opponent's Life Cloth into their trash.\n@A $T1 %R %X: If you have two or fewer cards in your Life Cloth, target red SIGNI on your field gains [[Assassin]] until end of turn.\n@A @[Exceed 4]@: Reveal two different Fesonne Magic Craft and add them to your LRIG Deck. "
        );
        
        setName("en_fan", "Yuzuki Three, Flaming Swimming Flower");
        setDescription("en_fan",
                "@E @[Crush 1 of your life cloth]@: Put 1 of your opponent's life cloth into the trash.\n" +
                "@A $T1 %R %X: If you have 2 or less life cloth, target 1 of your red SIGNI, and until end of turn, it gains [[Assassin]].\n" +
                "@A @[Exceed 4]@: Reveal 2 different Fessone Magic crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "炎泳华  游月·灿");
        setDescription("zh_simplified", 
                "@E 生命护甲1张击溃:对战对手的生命护甲1张放置到废弃区。\n" +
                "@A $T1 %R%X:你的生命护甲在2张以下的场合，你的红色的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n" +
                "@A @[超越 4]@:从音乐节魔术的衍生把2种类各1张公开加入分身牌组。（音乐节魔术有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new CrushCost(1), this::onEnterEff);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            act1.setCondition(this::onActionEff1Cond);
            
            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }
        
        private void onEnterEff()
        {
            trash(getOpponent(), CardLocation.LIFE_CLOTH);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return getLifeClothCount(getOwner()) <= 2 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff1()
        {
            if(getLifeClothCount(getOwner()) <= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)).get();
                if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff2()
        {
            playerChoiceFessoneMagic();
        }
    }
}
