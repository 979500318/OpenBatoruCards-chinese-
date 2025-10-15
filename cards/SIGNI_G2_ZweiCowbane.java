package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G2_ZweiCowbane extends Card {
    
    public SIGNI_G2_ZweiCowbane()
    {
        setImageSets("WXDi-P02-076");
        
        setOriginalName("ツヴァイ＝ドクゼリ");
        setAltNames("ツヴァイドクゼリ Tsuvai Dokuzeri");
        setDescription("jp",
                "@A %G %K %X @[このシグニを場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Cicuta Virosa Type: Zwei");
        setDescription("en",
                "@A %G %K %X @[Put this SIGNI on your field into its owner's trash]@: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Zwei-Cowbane");
        setDescription("en_fan",
                "@A %G %K %X @[Put this SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "ZWEI=毒芹");
        setDescription("zh_simplified", 
                "@A %G%K%X这只精灵从场上放置到废弃区:对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)),
                new TrashCost()
            ), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
