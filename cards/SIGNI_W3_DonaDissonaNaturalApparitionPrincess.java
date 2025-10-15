package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_DonaDissonaNaturalApparitionPrincess extends Card {

    public SIGNI_W3_DonaDissonaNaturalApparitionPrincess()
    {
        setImageSets("WXDi-P12-044", "WXDi-P12-044P");

        setOriginalName("幻怪姫　ドーナ//ディソナ");
        setAltNames("ゲンカイキドーナディソナ Genkaiki Doona Disona");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの他の#Sのシグニのパワーを＋3000する。\n" +
                "@U：あなたのルリグ１体がアタックしたとき、あなたのアップ状態の#Sのシグニ２体をダウンし%W %Xを支払ってもよい。そうした場合、そのルリグをアップし、ターン終了時まで、そのルリグは能力を失う。"
        );

        setName("en", "Dona//Dissona, Phantom Spirit Queen");
        setDescription("en",
                "@C: During your opponent's turn, other #S SIGNI on your field get +3000 power.\n" +
                "@U: Whenever a LRIG on your field attacks, you may down two upped #S SIGNI on your field and pay %W %X. If you do, up the LRIG that attacked and it loses its abilities until end of turn."
        );
        
        setName("en_fan", "Dona//Dissona, Natural Apparition Princess");
        setDescription("en_fan",
                "@C: During your opponent's turn, all of your other #S @[Dissona]@ SIGNI get +3000 power.\n" +
                "@U: Whenever your LRIG attacks, you may down 2 of your upped #S @[Dissona]@ SIGNI and pay %W %X. If you do, up that LRIG, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "幻怪姬 多娜//失调");
        setDescription("zh_simplified", 
                "@C 对战对手的回合期间，你的其他的#S的精灵的力量+3000。\n" +
                "@U 当你的分身1只攻击时，可以把你的竖直状态的#S的精灵2只#D并支付%W%X。这样做的场合，那只分身竖直，直到回合结束时为止，那只分身的能力失去。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().dissona().except(cardId), new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.DOWN).own().SIGNI().dissona().upped());

            if(data.size() == 2 && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)) && down(data) == 2)
            {
                up(caller);
                disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
    }
}
