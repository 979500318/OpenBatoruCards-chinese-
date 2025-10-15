package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_CodeMazeMuzicaDissona extends Card {

    public SIGNI_K1_CodeMazeMuzicaDissona()
    {
        setImageSets("WXDi-P12-083", "SPDi01-88");

        setOriginalName("コードメイズ　ムジカ//ディソナ");
        setAltNames("コードメイズムジカディソナ Koodo Meizu Mujika Disona");
        setDescription("jp",
                "@E @[エナゾーンから#Sのカード１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Muzica//Dissona, Code: Maze");
        setDescription("en",
                "@E @[Put a #S card from your Ener Zone into your trash]@: Target SIGNI on your opponent's field gets --3000 power until end of turn.\n"
        );
        
        setName("en_fan", "Code Maze Muzica//Dissona");
        setDescription("en_fan",
                "@E @[Put 1 #S @[Dissona]@ card from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "迷宫代号 穆希卡//失调");
        setDescription("zh_simplified", 
                "@E 从能量区把#S的牌1张放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(new TrashCost(new TargetFilter().dissona().fromEner()), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
