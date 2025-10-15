package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_HeikePhantomLightInsect extends Card {

    public SIGNI_K1_HeikePhantomLightInsect()
    {
        setImageSets("WXDi-P11-079");

        setOriginalName("幻光蟲　ヘイケ");
        setAltNames("ゲンコウチュウヘイケ Genkouchuu Heike");
        setDescription("jp",
                "@A %K %K @[このシグニを場からトラッシュに置く]@：カードが付いているか下にカードがある対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Heike Firefly, Phantom Light Insect");
        setDescription("en",
                "@A %K %K @[Put this SIGNI on your field into its owner's trash]@: Target SIGNI on your opponent's field with a card attached or a card underneath it gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Heike, Phantom Light Insect");
        setDescription("en_fan",
                "@A %K %K @[Put this SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI with a card attached to it or a card under it, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "幻光虫 平家萤");
        setDescription("zh_simplified", 
                "@A %K %K这只精灵从场上放置到废弃区:有牌附加或下面有牌的对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 2)), new TrashCost()), this::onActionEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withCardsUnder(CardUnderCategory.UNDER.getFlags() | CardUnderCategory.ATTACHED.getFlags())).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}

