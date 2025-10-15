package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class SIGNI_W1_CodeMazePeilaviDissona extends Card {

    public SIGNI_W1_CodeMazePeilaviDissona()
    {
        setImageSets("WXDi-P12-058");

        setOriginalName("コードメイズ　ペイラビ//ディソナ");
        setAltNames("コードメイズペイラビディソナ Koodo Meizu Peirabi Disona");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1ターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "$$2カードを２枚引く。"
        );

        setName("en", "Pale Laby//Dissona, Code: Maze");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field loses its abilities until end of turn." +
                "~#Choose one -- \n$$1 All SIGNI on your opponent's field lose their abilities until end of turn. \n$$2 Draw two cards."
        );
        
        setName("en_fan", "Code Maze Peilavi//Dissona");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, all of your opponent's SIGNI lose their abilities.\n" +
                "$$2 Draw 2 cards."
        );

		setName("zh_simplified", "迷宫代号  无眼怪//失调");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。" +
                "~#以下选1种。\n" +
                "$$1 直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "$$2 抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            } else {
                draw(2);
            }
        }
    }
}
