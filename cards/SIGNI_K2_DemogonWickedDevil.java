package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_DemogonWickedDevil extends Card {

    public SIGNI_K2_DemogonWickedDevil()
    {
        setImageSets("WXDi-P15-097");

        setOriginalName("凶魔　デモゴーン");
        setAltNames("キョウマデモゴーン Kyouma Demogoon");
        setDescription("jp",
                "@E @[他の＜悪魔＞のシグニ１体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Demogorn, Doomed Evil");
        setDescription("en",
                "@E @[Put another <<Demon>> SIGNI on your field into its owner's trash]@: Target SIGNI on your opponent's field gets --3000 power until end of turn.\n"
        );
        
        setName("en_fan", "Demogon, Wicked Devil");
        setDescription("en_fan",
                "@E @[Put 1 of your other <<Devil>> SIGNI from the field into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "凶魔 狄摩高根");
        setDescription("zh_simplified", 
                "@E 其他的&lt;恶魔&gt;精灵1只从场上放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL).except(cardId)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
