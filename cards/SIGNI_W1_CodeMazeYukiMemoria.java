package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.game.FieldZone;

public final class SIGNI_W1_CodeMazeYukiMemoria extends Card {
    
    public SIGNI_W1_CodeMazeYukiMemoria()
    {
        setImageSets("WXDi-P06-045", "WXDi-P06-045P", "SPDi10-08","SPDi38-19");
        
        setOriginalName("コードメイズ　ユキ//メモリア");
        setAltNames("コードメイズユキメモリア Koodo Meirzo Yuki Memoria");
        setDescription("jp",
                "@E：以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "$$2対戦相手のシグニ１体を対象とし、それを他のシグニゾーン１つに配置する。"
        );
        
        setName("en", "Yuki//Memoria, Code: Maze");
        setDescription("en",
                "@E: Choose one of the following.\n" +
                "$$1 Target SIGNI on your opponent's field loses its abilities until end of turn.\n" +
                "$$2 Move target SIGNI on your opponent's field to a different SIGNI Zone. "
        );
        
        setName("en_fan", "Code Maze Yuki//Memoria");
        setDescription("en_fan",
                "@E: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and move it onto 1 of their other SIGNI zones."
        );
        
		setName("zh_simplified", "迷宫代号 雪//回忆");
        setDescription("zh_simplified", 
                "@E :从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "$$2 对战对手的精灵1只作为对象，将其往其他的精灵区1个配置。（已经有精灵的精灵区不能配置）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(3000);
        
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
        }
        
        private void onEnterEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI()).get();
                
                if(target != null)
                {
                    FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.MOVE).OP().SIGNI().unoccupied()).get();
                    if(fieldZone != null) moveToZone(target, fieldZone.getZoneLocation());
                }
            }
        }
    }
}
