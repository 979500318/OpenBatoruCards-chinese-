package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_CodeAntiquePartheno extends Card {

    public SIGNI_K3_CodeAntiquePartheno()
    {
        setImageSets("WX25-P1-061", "WX25-P1-061U");
        setLinkedImageSets("WX25-P1-030");

        setOriginalName("コードアンティーク　パルテノ");
        setAltNames("コードアンティークパルテノ Koodo Antiiku Paruteno");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体がトラッシュから場に出たとき、あなたの場に《冒険の鍵主　ウムル＝トレ》がいる場合、ターン終了時まで、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、そのパワーを－8000する。@@を得る。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。その後、この方法でトラッシュに置かれたカードの中から＜古代兵器＞のシグニ１枚を対象とし、%Kを支払ってもよい。そうした場合、それを場に出す。"
        );

        setName("en", "Code Antique Partheno");
        setDescription("en",
                "@U $T1: When 1 of your SIGNI enters the field from your trash, if your LRIG is \"Umr-Tre, Wielder of The Key of Adventure\", until end of turn, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.@@" +
                "@E: Put the top 3 cards of your deck into the trash. Then, target 1 <<Ancient Weapon>> SIGNI from among the cards that were put into the trash this way, and you may pay %K. If you do, put it onto the field."
        );

		setName("zh_simplified", "古魂代号 帕特农");
        setDescription("zh_simplified", 
                "@U $T1 当你的精灵1只从废弃区出场时，你的场上有《冒険の鍵主:ウムル＝トレ》的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。@@\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。然后，从这个方法放置到废弃区的牌中把<<古代兵器>>精灵1张作为对象，可以支付%K。这样做的场合，将其出场。\n"
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

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getOldLocation() == CardLocation.TRASH ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("冒険の鍵主　ウムル＝トレ"))
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromTrash().playable().match(data)).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                putOnField(target);
            }
        }
    }
}
