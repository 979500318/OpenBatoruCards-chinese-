package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_GuillotineDissonaMediumEquipment extends Card {

    public SIGNI_W2_GuillotineDissonaMediumEquipment()
    {
        setImageSets("WXDi-P12-060");

        setOriginalName("中装　ギロチン//ディソナ");
        setAltNames("チュウソウギロチンディソナ Chuusou Girochin Disona");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの他の#Sのシグニのパワーを＋2000する。\n" +
                "@E %X：次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Guillotine//Dissona, High Armed");
        setDescription("en",
                "@C: During your opponent's turn, other #S SIGNI on your field get +2000 power.\n@E %X: This SIGNI gets +5000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Guillotine//Dissona, Medium Equipment");
        setDescription("en_fan",
                "@C: During your opponent's turn, all of your other #S @[Dissona]@ SIGNI get +2000 power.\n" +
                "@E %X: Until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "中装 断头台//失调");
        setDescription("zh_simplified", 
                "@C 对战对手的回合期间，你的其他的#S的精灵的力量+2000。\n" +
                "@E %X:直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().dissona().except(cardId), new PowerModifier(2000));
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
