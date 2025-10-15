package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K3_CodeOldCostaric extends Card {

    public SIGNI_K3_CodeOldCostaric()
    {
        setImageSets("WX25-P1-108");

        setOriginalName("コードオールド　コスタリク");
        setAltNames("コードオールドコスタリク Koodo Oorudo Kosutariku");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。このシグニがトラッシュから場に出ていた場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Code Old Costaric");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If this SIGNI entered the field from your trash, instead until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "古卒代号 哥斯达黎加");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。这只精灵从废弃区出场的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null) gainPower(target, getCardIndex().getPreTransientLocation() != CardLocation.TRASH ? -3000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
