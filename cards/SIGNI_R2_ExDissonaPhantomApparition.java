package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_ExDissonaPhantomApparition extends Card {

    public SIGNI_R2_ExDissonaPhantomApparition()
    {
        setImageSets("WXDi-P13-065");

        setOriginalName("幻怪　エクス//ディソナ");
        setAltNames("ゲンカイエクスディソナ Genkai Ekusu Disona");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。\n" +
                "@A $T1 %R %R：このシグニの下に#Sのカードが置かれている場合、ターン終了時まで、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Ex//Dissona, Phantom Spirit");
        setDescription("en",
                "@E: Put the top card of your deck under this SIGNI.\n@A $T1 %R %R: If there is a #S card underneath this SIGNI, it gains@>@U: Whenever this SIGNI attacks, vanish target SIGNI on your opponent's field with power 12000 or less.@@until end of turn." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Ex//Dissona, Phantom Apparition");
        setDescription("en_fan",
                "@E: Put the top card of your deck under this SIGNI.\n" +
                "@A $T1 %R %R: If there is a #S @[Dissona]@ card under this SIGNI, until end of turn, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 12000 or less, and banish it.@@" +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "幻怪 艾克斯//失调");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到这只精灵的下面。\n" +
                "@A $T1 %R %R这只精灵的下面有#S的牌放置的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量12000以下的精灵1只作为对象，将其破坏。@@" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(5000);

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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
        
        private void onActionEff()
        {
            if(new TargetFilter().own().dissona().under(getCardIndex()).getValidTargetsCount() > 0)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                
                attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}

