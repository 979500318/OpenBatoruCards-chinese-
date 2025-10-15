package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_HinaSorasakiDress extends Card {

    public SIGNI_K3_HinaSorasakiDress()
    {
        setImageSets("WX25-CP1-048");

        setOriginalName("空崎ヒナ(ドレス)");
        setAltNames("ソラサキヒナドレス Sorasaki Hina Doresu");
        setDescription("jp",
                "@A %K #D：ターン終了時まで、このシグニは@>@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。@@を得る。" +
                "~{{U：あなたのアタックフェイズ開始時、このシグニをアップする。"
        );

        setName("en", "Sorasaki Hina (Dress)");

        setName("en_fan", "Hina Sorasaki (Dress)");
        setDescription("en",
                "@A %K #D: Until end of turn, this SIGNI gains:" +
                "@>@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, target 1 of your opponent's SIGNI, and until end of turn, it gets --15000 power.@@" +
                "~{{U: At the beginning of your attack phase, up this SIGNI."
        );

		setName("zh_simplified", "空崎日奈(礼服)");
        setDescription("zh_simplified", 
                "@A %K#D:直到回合结束时为止，这只精灵得到\n" +
                "@>@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-15000。@@\n" +
                "~{{U:你的攻击阶段开始时，这只精灵竖直。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 1)), new DownCost()), this::onActionEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAutoEffCond);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -15000, ChronoDuration.turnEnd());
            }
        }
        
        private void onAutoEff(CardIndex caller)
        {
            up();
        }
    }
}
