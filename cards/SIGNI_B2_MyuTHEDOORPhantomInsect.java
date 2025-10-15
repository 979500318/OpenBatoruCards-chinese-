package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.PutUnderCost;

public final class SIGNI_B2_MyuTHEDOORPhantomInsect extends Card {

    public SIGNI_B2_MyuTHEDOORPhantomInsect()
    {
        setImageSets("WXDi-P15-066");

        setOriginalName("幻蟲　ミュウ//THE DOOR");
        setAltNames("ゲンチュウミュウザドアー Genchuu Myuu Za Doaa");
        setDescription("jp",
                "@E %B：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@E @[手札から＜解放派＞のシグニ１枚をこのシグニの下に置く]@：カードを１枚引く。"
        );

        setName("en", "Myu//THE DOOR, Phantom Insect");
        setDescription("en",
                "@E %B: Target SIGNI on your opponent's field gets --2000 power until end of turn.\n@E @[Put a <<Liberation Division>> SIGNI from your hand under this SIGNI]@: Draw a card.\n"
        );
        
        setName("en_fan", "Myu//THE DOOR, Phantom Insect");
        setDescription("en_fan",
                "@E %B: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@E @[Put 1 <<Liberation Faction>> from your hand under this SIGNI]@: Draw 1 card."
        );

		setName("zh_simplified", "幻虫 缪//THE DOOR");
        setDescription("zh_simplified", 
                "@E %B:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@E 从手牌把<<解放派>>精灵1张放置到这只精灵的下面:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff1);
            
            registerEnterAbility(new PutUnderCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromHand()), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            draw(1);
        }
    }
}
