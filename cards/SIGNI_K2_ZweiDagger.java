package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K2_ZweiDagger extends Card {
    
    public SIGNI_K2_ZweiDagger()
    {
        setImageSets("WXDi-P01-085");
        
        setOriginalName("ツヴァイ＝ダガ");
        setAltNames("ツヴァイダガ Tsuvai Daga");
        setDescription("jp",
                "@A #D：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。あなたのトラッシュにカードが１５枚以上ある場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Dagger Type: Zwei");
        setDescription("en",
                "@A #D: Target SIGNI on your opponent's field gets --2000 power until end of turn. If you have fifteen or more cards in your trash, it gets --5000 power until end of turn instead."
        );
        
        setName("en_fan", "Zwei-Dagger");
        setDescription("en_fan",
                "@A #D: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. If there are 15 or more cards in your trash, instead until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "ZWEI=毒匕首");
        setDescription("zh_simplified", 
                "@A 横置:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。你的废弃区的牌在15张以上的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, getTrashCount(getOwner()) < 15 ? -2000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
