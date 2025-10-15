package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CrushCost;
import open.batoru.game.gfx.GFXFieldBackground;

public final class ARTS_R_RidingThroughTheFlames extends Card {

    public ARTS_R_RidingThroughTheFlames()
    {
        setImageSets("WX25-P2-003", "WX25-P2-003U");

        setOriginalName("走行車炎");
        setAltNames("ソウコウシャエン Soukoushaen");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@U：対戦相手のライフバーストが発動したとき、対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。\n" +
                "@A $T1 @[ライフクロス１枚をクラッシュする]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Riding Through the Flames");
        setDescription("en",
                "During this game, you gain the following abilities:" +
                "@>@U: Whenever your opponent's Life Burst activates, target 1 card from your opponent's ener zone, and put it into the trash.\n" +
                "@A $T1 @[Crush 1 of your life cloth]@: Target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "走行车炎");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U :当对战对手的生命迸发发动时，从对战对手的能量区把1张牌作为对象，将其放置到废弃区。\n" +
                "@A $T1 :生命护甲1张击溃对战对手的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.LIFEBURST, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            GFXFieldBackground.attachToAbility(attachedAuto, new GFXFieldBackground(getOwner(), CardLocation.LRIG, "aura_fire"));
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
            
            ActionAbility attachedAct = new ActionAbility(new CrushCost(1), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachPlayerAbility(getOwner(), attachedAct, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            trash(target);
        }
        private void onAttachedActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}

