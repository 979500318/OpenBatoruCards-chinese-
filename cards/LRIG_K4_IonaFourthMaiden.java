package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckCanNewlyPutSIGNIOnField;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;
import open.batoru.game._3d.Group3D;
import open.batoru.game.animations.AnimationSpinnerRotateCustom;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXSphericalSpinnerObject;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_K4_IonaFourthMaiden extends Card {

    public LRIG_K4_IonaFourthMaiden()
    {
        setImageSets("WX24-P4-024", "WX24-P4-024U");

        setOriginalName("フォース/メイデン　イオナ");
        setAltNames("フォースメイデンイオナ Foosu Meiden Iona");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜イオナ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのトラッシュからカード１枚を対象とし、それを手札に加える。\n" +
                "@A $G1 @[@|クレイヴ|@]@ %K0：&E４枚以上@0対戦相手のシグニゾーン１つを指定する。このターン、そのシグニゾーンにあるシグニのパワーを－3000する。次のターンの間、対戦相手はそのシグニゾーンにシグニを新たに配置できない。"
        );

        setName("en", "Iona, Fourth/Maiden");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Iona>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Target 1 card from your trash, and add it to your hand.\n" +
                "@A $G1 @[@|Crave|@]@ %K0: &E4 or more@0 Choose 1 of your opponent's SIGNI zones. This turn, the SIGNI in that SIGNI zone get --3000 power. During the next turn, your opponent can't newly put SIGNI onto that zone."
        );

		setName("zh_simplified", "原力/少女 伊绪奈");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<イオナ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你的废弃区把1张牌作为对象，将其加入手牌。\n" +
                "@A $G1 @[@|渴望|@]@%K0&E4张以上@0对战对手的精灵区1个指定。这个回合，那个精灵区的精灵的力量-3000。下一个回合期间，对战对手不能在那个精灵区把精灵新配置。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(4);
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.IONA).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Crave");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromTrash()).get();
            addToHand(target);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI()).get();
                
                ConstantAbilityShared attachedConst1 = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().fromLocation(fieldZone.getZoneLocation()), new PowerModifier(-3000));
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                
                Group3D[] spinNodes = new Group3D[4];
                for(int i=0;i<spinNodes.length;i++) spinNodes[i] = new GFXSphericalSpinnerObject(25,60, 10, new int[]{160, 140, 220});
                GFXZoneSpinner spinner = new GFXZoneSpinner(getOpponent(),fieldZone.getZoneLocation(), new AnimationSpinnerRotateCustom(6000, 120, -50), spinNodes);
                GFXZoneSpinner.attachToChronoRecord(record, spinner);
                
                ConstantAbility attachedConst2 = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_NEWLY_PUT_SIGNI_ON_FIELD, TargetFilter.HINT_OWNER_OP, data ->
                    RuleCheckCanNewlyPutSIGNIOnField.getDataZoneSIGNIPosition(data) == SIGNIZonePosition.getSIGNIPositionByCardLocation(fieldZone.getZoneLocation()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                ));
                GFX.attachToAbility(attachedConst2, new GFXZoneUnderIndicator(getOpponent(),fieldZone.getZoneLocation(), "cracks", 0, new int[]{160, 140, 220}));
                attachedConst2.setCondition(this::onAttachedConst2Cond);
                
                attachPlayerAbility(getOwner(), attachedConst1, record);
                attachPlayerAbility(getOwner(), attachedConst2, ChronoDuration.turnEnd().repeat(2));
            }
        }
        private ConditionState onAttachedConst2Cond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
