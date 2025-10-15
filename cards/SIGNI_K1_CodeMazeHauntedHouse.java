package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.game.FieldZone;

public final class SIGNI_K1_CodeMazeHauntedHouse extends Card {

    public SIGNI_K1_CodeMazeHauntedHouse()
    {
        setImageSets("WX24-P2-089");

        setOriginalName("コードメイズ　オバケヤシキ");
        setAltNames("コードメイズオバケヤシキ Koodo Meizu Obakeyashiki Ghost House");
        setDescription("jp",
                "@E @[エナゾーンから＜迷宮＞のシグニ１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。それを他のシグニゾーン１つに配置してもよい。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Code Maze Haunted House");
        setDescription("en",
                "@E @[Put 1 <<Labyrinth>> SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. You may move it onto 1 of your opponent's other SIGNI zones." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "迷宫代号 鬼屋");
        setDescription("zh_simplified", 
                "@E 从能量区把<<迷宮>>精灵1张放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。可以将其往其他的精灵区1个配置。（力量在0以下，移动后因为规则被破坏）" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LABYRINTH).fromEner()), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null)
            {
                gainPower(target, -3000, ChronoDuration.turnEnd());
                
                FieldZone fieldZone = playerTargetZone(0,1, new TargetFilter(TargetHint.MOVE).OP().SIGNI().unoccupied()).get();
                if(fieldZone != null) moveToZone(target, fieldZone.getZoneLocation());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
