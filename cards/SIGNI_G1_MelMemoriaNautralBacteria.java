package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G1_MelMemoriaNautralBacteria extends Card {

    public SIGNI_G1_MelMemoriaNautralBacteria()
    {
        setImageSets("WXDi-P10-066", "WXDi-P10-066P", "SPDi01-95");

        setOriginalName("羅菌　メル//メモリア");
        setAltNames("ラキンメルメモリア Rakin Meru Memoria");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。\n\n" +
                "@A @[エナゾーンからこのカードをトラッシュに置く]@：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。"
        );

        setName("en", "Mel//Memoria, Natural Bacteria");
        setDescription("en",
                "@E: Target SIGNI on your field gets +3000 power until end of turn.\n\n" +
                "@A @[Put this card from your Ener Zone into your trash]@: Target SIGNI on your field gets +3000 power until end of turn."
        );
        
        setName("en_fan", "Mel//Memoria, Natural Bacteria");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and until end of turn, it gets +3000 power.\n\n" +
                "@A @[Put this card from your ener zone into the trash]@: Target 1 of your SIGNI, and until end of turn, it gets +3000 power."
        );

		setName("zh_simplified", "罗菌 梅露//回忆");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n" +
                "@A 从能量区把这张牌放置到废弃区:你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
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
            
            ActionAbility act = registerActionAbility(new TrashCost(), this::onEnterEff);
            act.setActiveLocation(CardLocation.ENER);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
        }
    }
}
