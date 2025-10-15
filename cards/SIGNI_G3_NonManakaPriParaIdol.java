package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_NonManakaPriParaIdol extends Card {

    public SIGNI_G3_NonManakaPriParaIdol()
    {
        setImageSets("WXDi-P10-069");

        setOriginalName("プリパラアイドル　真中のん");
        setAltNames("プリパラアイドルマナカノン Puripara Aidoru Manaka Non");
        setDescription("jp",
                "@U：あなたのターン終了時、【エナチャージ１】をする。あなたの場に＜プリパラ＞のシグニが３体ある場合、代わりに【エナチャージ２】をする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Manaka Non, Pripara Idol");
        setDescription("en",
                "@U: At the end of your turn, [[Ener Charge 1]]. If there are three <<Pripara>> SIGNI on your field, instead [[Ener Charge 2]]." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 [[Ener Charge 1]] "
        );
        
        setName("en_fan", "Non Manaka, PriPara Idol");
        setDescription("en_fan",
                "@U: At the end of your turn, [[Ener Charge 1]]. If there are 3 <<PriPara>> SIGNI on your field, instead [[Ener Charge 2]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "美妙天堂偶像 真中音");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，[[能量填充1]]。你的场上的<<プリパラ>>精灵在3只的场合，作为替代，[[能量填充2]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).getValidTargetsCount() != 3 ? 1 : 2);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
