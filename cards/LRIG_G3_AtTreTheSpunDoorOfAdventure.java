package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class LRIG_G3_AtTreTheSpunDoorOfAdventure extends Card {

    public LRIG_G3_AtTreTheSpunDoorOfAdventure()
    {
        setImageSets("WX25-P1-026", "WX25-P1-026U","SPDi44-04");

        setOriginalName("紡ぎし冒険の扉　アト＝トレ");
        setAltNames("ツムギシボウケンノトビラアトトレ Tsumugishi Bouken no Tobira Ato Tore");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニがそれぞれ共通するクラスを持たない場合、【エナチャージ２】をする。\n" +
                "@A $G1 @[@|プライマル|@]@ %G0：あなたのエナゾーンからシグニを好きな枚数対象とし、それらがそれぞれ共通するクラスを持たない場合、それらを手札に加える。この方法でカードを５枚以上手札に加えた場合、次の対戦相手のターン終了時まで、このルリグは@>@C：あなたは対戦相手の効果によってダメージを受けない。@@を得る。"
        );

        setName("en", "At-Tre, The Spun Door of Adventure");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all of your SIGNI do not share a common class, [[Ener Charge 2]].\n" +
                "@A $G1 @[@|Primal|@]@ %G0: Target any number of SIGNI from your ener zone, and if all of them do not share a common class, add them to your hand. If you added 5 or more cards to your hand this way, until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: You can't be damaged by your opponent's effects."
        );

		setName("zh_simplified", "纺转的冒险扉 亚特=TRE");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵不持有共通类别的场合，[[能量填充2]]。\n" +
                "@A $G1 原始%G0:从你的能量区把精灵任意张数作为对象，这些不持有共通类别的场合，将这些加入手牌。这个方法把牌5张以上加入手牌的场合，直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :你不会因为对战对手的效果受到伤害。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Primal");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = new TargetFilter().own().SIGNI().getExportedData();
            
            if(!data.isEmpty() && !CardDataSIGNIClass.shareCommonClass(data))
            {
                enerCharge(2);
            }
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner());
            
            if(data.get() != null)
            {
                if(CardDataSIGNIClass.shareCommonClass(data)) return;
                
                int count = addToHand(data);
                if(count >= 5)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_BE_DAMAGED, TargetFilter.HINT_OWNER_OWN, this::onAttachedConstEffModRuleCheck));
                    GFX.attachToAbility(attachedConst, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "generic", new int[]{0,255,0}));
                    attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return !isOwnCard(data.getSourceCardIndex()) && data.getSourceAbility() != null ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}

