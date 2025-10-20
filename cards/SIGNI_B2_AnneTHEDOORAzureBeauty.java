package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.PutUnderCost;

public final class SIGNI_B2_AnneTHEDOORAzureBeauty extends Card {

    public SIGNI_B2_AnneTHEDOORAzureBeauty()
    {
        setImageSets("WXDi-P16-073");

        setOriginalName("蒼美　アン//THE DOOR");
        setAltNames("ソウビアンザドアー Soubi An Za Doaa");
        setDescription("jp",
                "@E @[手札から＜解放派＞のシグニ１枚をこのシグニの下に置く]@：カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Ann//THE DOOR, Azure Beauty");
        setDescription("en",
                "@E @[Put a <<Liberation Division>> SIGNI from your hand under this SIGNI]@: Draw a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Anne//THE DOOR, Azure Beauty");
        setDescription("en_fan",
                "@E @[Put 1 <<Liberation Faction>> SIGNI from your hand under this SIGNI]@: Draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "苍美 安//THE DOOR");
        setDescription("zh_simplified", 
                "@E 从手牌把<<解放派>>精灵1张放置到这只精灵的下面:抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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
            
            registerEnterAbility(new PutUnderCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromHand()), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
