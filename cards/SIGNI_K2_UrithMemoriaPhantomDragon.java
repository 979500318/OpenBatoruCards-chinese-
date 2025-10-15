package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExceedCost;

public final class SIGNI_K2_UrithMemoriaPhantomDragon extends Card {

    public SIGNI_K2_UrithMemoriaPhantomDragon()
    {
        setImageSets("WXDi-P10-075", "WXDi-P10-075P");

        setOriginalName("幻竜　ウリス//メモリア");
        setAltNames("ゲンリュウウリスメモリア Genryuu Urisu Memoria");
        setDescription("jp",
                "@U $T1：対戦相手のシグニ１体がバニッシュされたとき、あなたのデッキの一番上を見る。そのカードをトラッシュに置いてもよい。\n" +
                "@E @[エクシード３]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Urith//Memoria, Phantom Dragon");
        setDescription("en",
                "@U $T1: When a SIGNI on your opponent's field is vanished, look at the top card of your deck. You may put that card into your trash. \n" +
                "@E @[Exceed 3]@: Target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Urith//Memoria, Phantom Dragon");
        setDescription("en_fan",
                "@U $T1: When your opponent's SIGNI is banished, look at the top card of your deck. You may put that card into the trash.\n" +
                "@E @[Exceed 3]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "幻龙 乌莉丝//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵1只被破坏时，看你的牌组最上面。可以把那张牌放置到废弃区。\n" +
                "@E @[超越 3]@:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new ExceedCost(3), this::onEnterEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = look();
            
            if(cardIndex != null && playerChoiceActivate())
            {
                trash(cardIndex);
            } else {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
