package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_SkadiVerdantAngel extends Card {
    
    public SIGNI_G2_SkadiVerdantAngel()
    {
        setImageSets("WXDi-P01-073");
        
        setOriginalName("翠天　スカジ");
        setAltNames("スイテンスカジ Suiten Sukaji");
        setDescription("jp",
                "@E：あなたの他の＜天使＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、[[エナチャージ１]]をする。@@を得る。"
        );
        
        setName("en", "Skadi, Jade Angel");
        setDescription("en",
                "@E: Another target <<Angel>> SIGNI on your field gains@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@until end of turn."
        );
        
        setName("en_fan", "Skaði, Verdant Angel");
        setDescription("en_fan",
                "@E: Target 1 of your other <<Angel>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "翠天 斯卡蒂");
        setDescription("zh_simplified", 
                "@E :你的其他的<<天使>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.ANGEL).except(getCardIndex())).get();
            if(target != null)
            {
                AutoAbility attachedAbility = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAbility, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            enerCharge(1);
        }
    }
}
