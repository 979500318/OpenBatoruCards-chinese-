package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_G3_YukariKadenokoujiBoldnessOfYouth extends Card {

    public LRIG_G3_YukariKadenokoujiBoldnessOfYouth()
    {
        setImageSets("WX25-CP1-020", "WX25-CP1-020U");

        setOriginalName("勘解由小路ユカリ[まっすぐに芽生えた決意]");
        setAltNames("カデノコウジユカリマッスグニメバエタケツイ Kadenokouji Yukari Massugu ni Mebae Taketsui");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、【エナチャージ１】をする。あなたのエナゾーンにカードがない場合、代わりに【エナチャージ２】をする。\n" +
                "@A $G1 @[エナゾーンからすべてのカードをトラッシュに置く]@：この方法でカードを３枚以上トラッシュに置いた場合、対戦相手のライフクロス１枚をエナゾーンに置く。７枚以上トラッシュに置いた場合、追加で対戦相手のライフクロス１枚をエナゾーンに置く。" +
                "~{{A $G1 %G0：【エナチャージ２】"
        );
        
        setName("en", "Kadenokouji Yukari [Boldness of Youth]");

        setName("en_fan", "Yukari Kadenokouji [Boldness of Youth]");
        setDescription("en",
                "@U: At the beginning of your attack phase, [[Ener Charge 1]]. If there no cards in your ener zone, instead [[Ener Charge 2]].\n" +
                "@A $G1 @[Put all cards from your ener zone into the trash]@: If 3 or more cards were put into the trash this way, put 1 of your opponent's life cloth into the ener zone. If 7 or more cards were put into the trash this way, additionally put 1 of your opponent's life cloth into the ener zone." +
                "~{{A $G1 %G0: [[Ener Charge 2]]"
        );

		setName("zh_simplified", "勘解由小路紫[刚直的决心]");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，[[能量填充1]]。你的能量区没有牌的场合，作为替代，[[能量填充2]]。\n" +
                "@A $G1 从能量区把全部的牌放置到废弃区:这个方法把牌3张以上放置到废弃区的场合，对战对手的生命护甲1张放置到能量区。7张以上放置到废弃区的场合，追加对战对手的生命护甲1张放置到能量区。\n" +
                "~{{A$G1 %G0:[[能量填充2]]。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKARI);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act1 = registerActionAbility(new TrashCost(() -> getEnerCount(getOwner()), new TargetFilter().fromEner()), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(getEnerCount(getOwner()) > 0 ? 1 : 2);
        }

        private void onActionEff1()
        {
            if(getAbility().getCostPaidData() != null && getAbility().getCostPaidData().get() instanceof CardIndexSnapshot)
            {
                int count = getAbility().getCostPaidData().size();
                if(count >= 3) putInEner(getOpponent(), CardLocation.LIFE_CLOTH);
                if(count >= 7) putInEner(getOpponent(), CardLocation.LIFE_CLOTH);
            }
        }

        private void onActionEff2()
        {
            enerCharge(2);
        }
    }
}
