package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;

public final class LRIG_K3_NanashiPartThreeDance extends Card {

    public LRIG_K3_NanashiPartThreeDance()
    {
        setImageSets("WXDi-P09-009", "WXDi-P09-009U");

        setOriginalName("ナナシ　其ノ参ノ踊");
        setAltNames("ナナシソノサンノオド Nanashi Sono San no Odo");
        setDescription("jp",
                "@C：あなたのアタックフェイズの間、対戦相手の感染状態のシグニのパワーを－3000する。\n" +
                "@E：対戦相手のシグニゾーン１つに【ウィルス】１つを置く。\n" +
                "@A $G1 %K0：このターン終了時、あなたのすべてのシグニを裏向きにする。次の対戦相手のアタックフェイズ開始時、この方法で裏向きにしたシグニを、同じ場所にシグニがない場合、表向きにする。"
        );

        setName("en", "Nanashi, Part Three Dance");
        setDescription("en",
                "@C: During your attack phase, infected SIGNI on your opponent's field get --3000 power.\n" +
                "@E: Put a [[Virus]] in one of your opponent's SIGNI Zones.\n" +
                "@A $G1 %K0: At the end of this turn, turn all SIGNI on your field face down. At the beginning of your opponent's next attack phase, if a SIGNI is not in the same position as a SIGNI turned face down this way, turn that SIGNI face up."
        );

        setName("en_fan", "Nanashi Part Three Dance");
        setDescription("en_fan",
                "@C: During your attack phase, your opponent's infected SIGNI get --3000 power.\n" +
                "@E: Put 1 [[Virus]] on 1 of your opponent's SIGNI zones.\n" +
                "@A $G1 %K0: At the end of this turn, turn all of your SIGNI face down. At the beginning of your opponent's next attack phase, turn the SIGNI turned face down this way face up if there is no SIGNI in the same zone."
        );

		setName("zh_simplified", "无名 其之叁之踊");
        setDescription("zh_simplified", 
                "@C :你的攻击阶段期间，对战对手的感染状态的精灵的力量-3000。\n" +
                "@E :对战对手的精灵区1个放置[[病毒]]1个。\n" +
                "@A $G1 %K0:这个回合结束时，你的全部的精灵变为里向。下一个对战对手的攻击阶段开始时，这个方法里向的精灵，相同场所没有精灵的场合，变为表向。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().OP().SIGNI().infected(), new PowerModifier(-3000));

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onConstEffCond(CardIndex cardIndex)
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).OP().SIGNI().not(new TargetFilter().infected())).get();
            attachZoneObject(fieldZone, CardUnderType.ZONE_VIRUS);
        }
        
        private DataTable<CardIndex> data;
        private void onActionEff()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                data = getSIGNIOnField(getOwner());
                flip(data, CardFace.BACK);
            });
            callDelayedEffect(ChronoDuration.nextPhase(getOpponent(), GamePhase.ATTACK_PRE), () -> {
                flip(data, CardFace.FRONT);
            });
        }
    }
}
